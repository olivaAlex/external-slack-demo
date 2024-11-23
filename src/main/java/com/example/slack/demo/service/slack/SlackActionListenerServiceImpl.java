package com.example.slack.demo.service.slack;


import com.example.slack.demo.model.Rating;
import com.example.slack.demo.model.User;
import com.example.slack.demo.repository.RatingRepository;
import com.example.slack.demo.repository.UserRepository;
import com.slack.api.bolt.App;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.model.block.element.BlockElements;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.example.slack.demo.service.slack.SlackBlockBuilderServiceImpl.DANGER;


@Service
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
public class SlackActionListenerServiceImpl implements SlackActionListenerService {

    private final App app;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;

    public static final String IGNORE_BUTTON = "ignore-button";
    public static final String YES_BUTTON = "rate-yes";
    public static final String NO_BUTTON = "rate-no";
    public static final String STAR_RATING_PATTERN = "[1-5]-stars";

    public void actionListeners() {
        handleIgnoreButton();
        handleNoButton();
        handleYesButton();
        handleStarRatingButton();
    }

    private void handleIgnoreButton() {
        removeMessage(IGNORE_BUTTON);
    }

    private void handleNoButton() {
        removeMessage(NO_BUTTON);
    }

    private void removeMessage(String noButton) {
        app.blockAction(noButton, (req, ctx) -> {
            String messageTs = req.getPayload().getMessage().getTs();
            String channelId = req.getPayload().getContainer().getChannelId();

            ctx.client().chatDelete(r -> r.channel(channelId).ts(messageTs));
            return ctx.ack();
        });
    }

    private void handleYesButton() {
        app.blockAction(YES_BUTTON, (req, ctx) -> {
            String messageTs = req.getPayload().getMessage().getTs();
            String channelId = req.getPayload().getContainer().getChannelId();
            List<LayoutBlock> blocks = new ArrayList<>();

            blocks.add(createMarkDownTextBlock("Please rate our Slack bot:"));
            for (int i = 5; i >= 1; i--) {
                String actionId = i + "-stars";
                blocks.add(createButtonSection("⭐".repeat(i), actionId, "primary"));
            }
            blocks.add(createButtonSection("Cancel", IGNORE_BUTTON, "danger"));
            ctx.client().chatUpdate(r -> r.channel(channelId).ts(messageTs).blocks(blocks));
            return ctx.ack();
        });
    }

    private void handleStarRatingButton() {
        app.blockAction(Pattern.compile(STAR_RATING_PATTERN), (req, ctx) -> {
            String actionId = req.getPayload().getActions().get(0).getActionId();
            int ratingValue = Integer.parseInt(actionId.split("-")[0]);
            String username = req.getPayload().getUser().getUsername();
            String messageTs = req.getPayload().getMessage().getTs();
            String channelId = req.getPayload().getContainer().getChannelId();

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User with username '" + username + "' not found."));
            Rating rating = new Rating();
            rating.setRating(ratingValue);
            rating.setUser(user);
            ratingRepository.save(rating);

            List<LayoutBlock> blocks = new ArrayList<>();
            blocks.add(createMarkDownTextBlock("Thank you for rating our Slack bot! 🎉"));
            blocks.add(createButtonSection("Ignore", IGNORE_BUTTON, DANGER));
            ctx.client().chatUpdate(r -> r.channel(channelId).ts(messageTs).blocks(blocks));
            return ctx.ack();
        });
    }

    private LayoutBlock createMarkDownTextBlock(String text) {
        return SectionBlock.builder()
                .text(BlockCompositions.markdownText(text))
                .build();
    }

    private LayoutBlock createButtonSection(String text, String actionId, String style) {
        return ActionsBlock.builder()
                .elements(List.of(BlockElements.button(b -> b.text(BlockCompositions.plainText(pt -> pt.text(text)))
                        .actionId(actionId)
                        .style(style))))
                .build();
    }
}
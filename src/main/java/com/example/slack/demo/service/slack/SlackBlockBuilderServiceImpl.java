package com.example.slack.demo.service.slack;

import com.example.slack.demo.configuration.slack.SlackMessage;
import com.slack.api.model.block.ActionsBlock;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import com.slack.api.model.block.element.BlockElements;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SlackBlockBuilderServiceImpl implements SlackBuilderService {

    private final SlackApp slackApp;

    @Value("${application.settings.integration.slack.user-email}")
    private String defaultUserEmail;

    public static final String IGNORE_BUTTON = "ignore-button";
    public static final String DANGER = "danger";

    public ResponseEntity<Object> sendHelloMessageToBotWithoutButton() throws Exception {
        List<SlackMessage> slackMessages = new ArrayList<>();
        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(SectionBlock.builder()
                           .text(BlockCompositions.markdownText("Hello everyone! \uD83D\uDC4B"))
                           .build());
        slackMessages.add(SlackMessage.builder()
                                  .blocks(blocks)
                                  .userEmail(defaultUserEmail)
                                  .build());
        slackApp.sendMessage(slackMessages);
        return ResponseEntity.ok("The message was sent to the bot channel!");
    }

    public ResponseEntity<Object> sendHelloMessageToBotWithButton() throws Exception {
        List<SlackMessage> slackMessages = new ArrayList<>();
        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(SectionBlock.builder()
                           .text(BlockCompositions.markdownText(
                                   "Hello everyone! \uD83D\uDC4B Now you can ignore this message!"))
                           .build());
        this.addIgnoreButton(blocks);
        slackMessages.add(SlackMessage.builder()
                                  .blocks(blocks)
                                  .userEmail(defaultUserEmail)
                                  .build());
        slackApp.sendMessage(slackMessages);
        return ResponseEntity.ok("The message was sent to the bot channel!");
    }

    @Override
    public ResponseEntity<Object> rateSlackBot() {
        List<SlackMessage> slackMessages = new ArrayList<>();
        List<LayoutBlock> blocks = new ArrayList<>();

        blocks.add(SectionBlock.builder()
                           .text(BlockCompositions.markdownText("Would you like to rate our Slack bot?"))
                           .build());

        blocks.add(ActionsBlock.builder()
                           .elements(List.of(
                                   BlockElements.button(b -> b.text(BlockCompositions.plainText(pt -> pt.text("Yes")))
                                           .actionId("rate-yes")
                                           .style("primary")), // Green button
                                   BlockElements.button(b -> b.text(BlockCompositions.plainText(pt -> pt.text("No")))
                                           .actionId("rate-no")
                                           .style("danger"))    // Red button
                           ))
                           .build());

        slackMessages.add(SlackMessage.builder()
                                  .blocks(blocks)
                                  .userEmail(defaultUserEmail)
                                  .build());

        try {
            slackApp.sendMessage(slackMessages);
            return ResponseEntity.ok("The rating message was sent to the bot channel!");
        } catch (Exception e) {
            log.error("Failed to send the rating message", e);
            return ResponseEntity.status(500).body("Failed to send the rating message");
        }
    }

    private void addIgnoreButton(List<LayoutBlock> blocks) {
        blocks.add(createButtonSection("Ignore", IGNORE_BUTTON, DANGER));
    }

    private LayoutBlock createButtonSection(String text, String actionId, String style) {
        return ActionsBlock.builder()
                .elements(List.of(BlockElements.button(b -> b.text(BlockCompositions.plainText(pt -> pt.text(text)))
                        .actionId(actionId)
                        .style(style))))
                .build();
    }
}

package com.example.slack.demo.service.slack;


import com.slack.api.bolt.App;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Log4j2
@ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
public class SlackActionListenerServiceImpl implements SlackActionListenerService{

    private final App app;
    public static final String IGNORE_BUTTON = "ignore-button";

    public void actionListeners() {
        handleIgnoreButton();
    }

    private void handleIgnoreButton() {
        app.blockAction(IGNORE_BUTTON, (req, ctx) -> {
            String messageTs = req.getPayload().getMessage().getTs();
            String channelId = req.getPayload().getContainer().getChannelId();

            ctx.client().chatDelete(r -> r.channel(channelId).ts(messageTs));
            return ctx.ack();
        });
    }

}

package com.example.slack.demo.service.slack;

import com.example.slack.demo.configuration.slack.SlackMessage;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class SlackAppStub implements SlackApp {

    @Override
    public void sendMessage(List<SlackMessage> slackMessages) {
        slackMessages.forEach(slackMessage -> System.err.println("Slack is disabled. Message '"
                + slackMessage.getBlocks().toString() + "' wasn't sent to users: " + slackMessage.getUserEmail()));
    }
}

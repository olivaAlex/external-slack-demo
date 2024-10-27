package com.example.slack.demo.service.slack;


import com.example.slack.demo.configuration.slack.SlackMessage;

import java.util.List;

public interface SlackApp {

    void sendMessage(List<SlackMessage> slackMessages) throws Exception;

}
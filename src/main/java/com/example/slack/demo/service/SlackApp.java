package com.example.slack.demo.service;


import com.example.slack.demo.configuration.slack.SlackMessage;

import java.util.List;

public interface SlackApp {

    void sendMessage(List<SlackMessage> message) throws Exception;

}
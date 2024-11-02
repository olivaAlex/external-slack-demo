package com.example.slack.demo.configuration.slack;

import com.example.slack.demo.service.slack.SlackActionListenerService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@Log4j2
@AllArgsConstructor
@ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
public class SlackActionListenerConfig {

    private final SlackActionListenerService slackActionListenerService;


    @PostConstruct
    public void startListeningToActions() {
        slackActionListenerService.actionListeners();
    }

}
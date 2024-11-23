package com.example.slack.demo.controller;

import com.example.slack.demo.service.slack.SlackBuilderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SlackController {

    private final SlackBuilderService slackBuilderService;


    @Tag(name = "Slack")
    @Operation(summary = "Sends a welcome message to the bot without any button!")
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "/welcome")
    public ResponseEntity<Object> sendsWelcomeMessage() throws Exception {
        return slackBuilderService.sendHelloMessageToBotWithoutButton();
    }

    @Tag(name = "Slack")
    @Operation(summary = "Sends a welcome message to the bot with the ignore button!")
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "/welcome-with-button")
    public ResponseEntity<Object> sendsWelcomeMessageWithButton() throws Exception {
        return slackBuilderService.sendHelloMessageToBotWithButton();
    }

    @Tag(name = "Slack")
    @Operation(summary = "Sends a request to give a rating to the slack bot!")
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "/rate-this-slack-bot")
    public ResponseEntity<Object> rateThisPresentation() {
        return slackBuilderService.rateSlackBot();
    }
}

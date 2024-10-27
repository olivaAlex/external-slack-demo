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
    @Operation(summary = "Sends a welcome message to the bot!")
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE, path = "/welcome")
    public ResponseEntity<Object> sendsWelcomeMessage() throws Exception {
        return slackBuilderService.sendHelloMessageToBot();
    }
}

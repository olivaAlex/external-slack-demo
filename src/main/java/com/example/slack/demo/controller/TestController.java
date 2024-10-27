package com.example.slack.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class TestController {

    @Tag(name = "Slack")
    @Operation(summary = "Trigger slack notification")
    @GetMapping("/test")
    public String test() {
        return "Test endpoint is working!";
    }
}

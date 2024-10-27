package com.example.slack.demo.configuration;

import lombok.Data;
import org.jetbrains.annotations.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@ConfigurationProperties(prefix = "application.settings")
public class GeneralSettings {

    @NotNull
    private Integration integration;
//    @NotNull
//    private Slack slack;
    @Data
    public static class Slack {
        private String botToken;
        private String appToken;
        private boolean enabled;
    }


    @Data
    public static class Integration {
        @NotNull
        private Slack slack;
    }
}

package com.example.slack.demo.configuration.slack;

import com.example.slack.demo.configuration.GeneralSettings;
import com.example.slack.demo.service.slack.SlackApp;
import com.example.slack.demo.service.slack.SlackAppApi;
import com.example.slack.demo.service.slack.SlackAppStub;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SlackConfig {

    @Bean
    @ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
    public App getSlackBoltApp(GeneralSettings generalSettings) throws Exception {
        App app = new App(AppConfig.builder().singleTeamBotToken(generalSettings.getIntegration().getSlack().getBotToken()).build());
        String appToken = generalSettings.getIntegration().getSlack().getAppToken();
        SocketModeApp socketModeApp = new SocketModeApp(appToken, app);
        socketModeApp.startAsync();
        return app;
    }

    @Bean
    @ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
    public SlackHealthIndicator slackHealthIndicator(GeneralSettings generalSettings, App app) {
        return new SlackHealthIndicator(generalSettings, app);
    }

    @Bean
    @ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "true")
    public SlackApp slackApp(App app) {
        return new SlackAppApi(app);
    }

    @Bean
    @ConditionalOnProperty(name = "application.settings.integration.slack.enabled", havingValue = "false")
    public SlackApp slackAppStub() {
        return new SlackAppStub();
    }

}
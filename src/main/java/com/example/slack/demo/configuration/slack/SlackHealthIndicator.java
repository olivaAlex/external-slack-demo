package com.example.slack.demo.configuration.slack;

import com.example.slack.demo.configuration.GeneralSettings;
import com.slack.api.bolt.App;
import com.slack.api.methods.response.auth.AuthTestResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Status;

@RequiredArgsConstructor
public class SlackHealthIndicator extends AbstractHealthIndicator {

    private final GeneralSettings generalSettings;
    private final App app;

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        AuthTestResponse authTestResponse = app.client().authTest(authTestRequestBuilder -> authTestRequestBuilder.token(
                generalSettings.getIntegration().getSlack().getBotToken()));
        builder.status(authTestResponse.isOk() ? Status.UP : Status.DOWN);

    }
}

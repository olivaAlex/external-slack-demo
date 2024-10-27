package com.example.slack.demo.service;

import com.example.slack.demo.configuration.slack.SlackMessage;
import com.slack.api.bolt.App;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.response.users.UsersLookupByEmailResponse;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class SlackAppApi implements SlackApp {

    private final App app;

    @Override
    public void sendMessage(List<SlackMessage> slackMessages) throws Exception {
        Set<String> errorSendingEmails = new HashSet<>();

        slackMessages.forEach(message -> {
            try {
                UsersLookupByEmailResponse usersLookupByEmailResponse =
                        app.client().usersLookupByEmail(user -> user.email(message.getUserEmail()));

                if (usersLookupByEmailResponse == null || usersLookupByEmailResponse.getUser() == null) {
                    System.err.println("User not found for email: " + message.getUserEmail());
                    errorSendingEmails.add(message.getUserEmail());
                    return;
                }

                ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                        .channel(usersLookupByEmailResponse.getUser().getId())
                        .blocks(message.getBlocks())
                        .build();
                app.client().chatPostMessage(request);

            } catch (Exception e) {
                System.err.println("Error sending message to user with email: " + message.getUserEmail() + " Error: " + e);
                errorSendingEmails.add(message.getUserEmail());
            }
        });
        if (!errorSendingEmails.isEmpty()) {
            throw new Exception("Can't send message to users: " + errorSendingEmails);
        }
    }
}

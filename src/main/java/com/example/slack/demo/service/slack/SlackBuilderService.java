package com.example.slack.demo.service.slack;


import org.springframework.http.ResponseEntity;

public interface SlackBuilderService {

    ResponseEntity<Object> sendHelloMessageToBotWithoutButton() throws Exception;

    ResponseEntity<Object> sendHelloMessageToBotWithButton() throws Exception;
}

package com.example.slack.demo.service.slack;


import org.springframework.http.ResponseEntity;

public interface SlackBuilderService {

    ResponseEntity<Object> sendHelloMessageToBot() throws Exception;
}

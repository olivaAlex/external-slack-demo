package com.example.slack.demo.service.slack;


import com.example.slack.demo.configuration.slack.SlackMessage;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.SectionBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class SlackBlockBuilderServiceImpl implements SlackBuilderService {

    private final SlackApp slackApp;

    public ResponseEntity<Object> sendHelloMessageToBot() throws Exception {
        List<SlackMessage> slackMessages = new ArrayList<>();
        List<LayoutBlock> blocks = new ArrayList<>();
        blocks.add(SectionBlock.builder()
                           .text(BlockCompositions.markdownText("Hello everyone! \uD83D\uDC4B"))
                           .build());
        slackMessages.add(SlackMessage.builder()
                                  .blocks(blocks)
                                  .userEmail("aoliva@griddynamics.com")
                                  .build());
        slackApp.sendMessage(slackMessages);
        //TODO IMPLEMENT HANDLE AND ADD IGNORE BUTTON

        return ResponseEntity.ok("The message wsa sent to the bot channel!");
    }
}

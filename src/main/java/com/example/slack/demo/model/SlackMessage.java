package com.example.slack.demo.model;

import com.slack.api.model.block.LayoutBlock;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SlackMessage {
    private String userEmail;
    private List<LayoutBlock> blocks;
}

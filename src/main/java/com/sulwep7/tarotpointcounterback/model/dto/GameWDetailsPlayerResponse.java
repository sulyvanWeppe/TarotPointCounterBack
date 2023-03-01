package com.sulwep7.tarotpointcounterback.model.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GameWDetailsPlayerResponse {
    private String playerName;
    private int playerScore;
}

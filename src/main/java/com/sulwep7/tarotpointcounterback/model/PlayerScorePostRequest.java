package com.sulwep7.tarotpointcounterback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerScorePostRequest {

    private String playerName;
    private int playerScore;
}

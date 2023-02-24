package com.sulwep7.tarotpointcounterback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PlayerScore {
    private int gameId;
    private String playerName;
    private int playerScore;
}

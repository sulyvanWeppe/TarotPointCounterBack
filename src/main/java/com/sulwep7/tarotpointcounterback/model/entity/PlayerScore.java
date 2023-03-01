package com.sulwep7.tarotpointcounterback.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PlayerScore {
    private String gameUuid;
    private String playerName;
    private int playerScore;
}

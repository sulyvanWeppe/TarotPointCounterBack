package com.sulwep7.tarotpointcounterback.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@Builder
public class GameWDetails {
    private String gameUuid;
    private Timestamp timestamp;
    private int nrPlayers;
    private String playerName;
    private int playerScore;
}

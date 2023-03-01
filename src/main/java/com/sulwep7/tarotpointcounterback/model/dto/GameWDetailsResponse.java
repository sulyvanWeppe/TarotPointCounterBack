package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;

@Builder
@Getter
public class GameWDetailsResponse {
    private String gameUuid;
    private Timestamp timestamp;
    private int nrPlayers;
    @JsonProperty("players")
    private List<GameWDetailsPlayerResponse> gameWDetailsPlayerResponseList;
}

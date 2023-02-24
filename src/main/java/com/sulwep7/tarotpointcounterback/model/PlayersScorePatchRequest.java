package com.sulwep7.tarotpointcounterback.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PlayersScorePatchRequest {
    private int gameId;
    @JsonProperty("playersScore")
    private List<PlayerScorePatchRequest> playerScorePatchRequests;
}

package com.sulwep7.tarotpointcounterback.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
public class PlayersScorePatchRequest {
    private String gameUuid;
    @JsonProperty("playersScore")
    private List<PlayerScorePatchRequest> playerScorePatchRequests;
}

package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class PlayersScorePatchRequest {
    private String gameUuid;
    @JsonProperty("playersScore")
    private List<PlayerScorePatchRequest> playerScorePatchRequests;
}

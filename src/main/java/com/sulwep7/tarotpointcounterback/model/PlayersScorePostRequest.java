package com.sulwep7.tarotpointcounterback.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PlayersScorePostRequest {
    private String gameUuid;

    @JsonProperty("playersScore")
    private List<PlayerScorePostRequest> playerScorePostRequestList;

}

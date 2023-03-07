package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@ApiModel
public class GameWDetailsResponse {
    @ApiModelProperty(example = "65b68079-f5de-44f9-b871-9c46b0a8e85a")
    private String gameUuid;
    @ApiModelProperty(dataType = "Timestamp",example = "2018-11-21T06:20:32.232Z")
    private Timestamp timestamp;
    @ApiModelProperty(example = "4")
    private int nrPlayers;
    @JsonProperty("players")
    private List<GameWDetailsPlayerResponse> gameWDetailsPlayerResponseList;
}

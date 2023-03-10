package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ApiModel
public class PlayersScorePatchRequest {
    @ApiModelProperty(example = "65b68079-f5de-44f9-b871-9c46b0a8e85a")
    private String gameUuid;
    @JsonProperty("playersScore")
    private List<PlayerScorePatchRequest> playerScorePatchRequests;
}

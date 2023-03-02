package com.sulwep7.tarotpointcounterback.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel
public class GameWDetailsPlayerResponse {
    @ApiModelProperty(example = "Player 1")
    private String playerName;
    @ApiModelProperty(example = "10")
    private int playerScore;
}

package com.sulwep7.tarotpointcounterback.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@ApiModel
public class PlayerScore {
    @ApiModelProperty(example = "65b68079-f5de-44f9-b871-9c46b0a8e85a")
    private String gameUuid;
    @ApiModelProperty(example = "Player 1")
    private String playerName;
    @ApiModelProperty(example = "10")
    private int playerScore;
}

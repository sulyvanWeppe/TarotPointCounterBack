package com.sulwep7.tarotpointcounterback.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel
public class PlayerScorePostRequest {
    @ApiModelProperty(example = "Player 1")
    private String playerName;
    @ApiModelProperty(example = "10")
    private int playerScore;
}

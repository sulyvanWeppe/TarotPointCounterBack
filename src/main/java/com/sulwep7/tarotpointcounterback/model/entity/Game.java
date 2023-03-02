package com.sulwep7.tarotpointcounterback.model.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ApiModel
public class Game {
    @ApiModelProperty(example = "4")
    private int nrPlayers;
}

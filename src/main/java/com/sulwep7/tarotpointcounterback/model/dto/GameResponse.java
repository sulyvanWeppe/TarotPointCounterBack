package com.sulwep7.tarotpointcounterback.model.dto;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
@ApiModel
public class GameResponse extends Game {
    @ApiModelProperty(example = "65b68079-f5de-44f9-b871-9c46b0a8e85a")
    private String uuid;
    @ApiModelProperty(example = "2023-03-02 14:30:23.280009788")
    private Timestamp timestamp;
}

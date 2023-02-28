package com.sulwep7.tarotpointcounterback.model.dto;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class GameResponse extends Game {
    private String uuid;
    private Timestamp timestamp;
}

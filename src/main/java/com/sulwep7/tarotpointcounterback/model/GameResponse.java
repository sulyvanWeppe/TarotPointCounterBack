package com.sulwep7.tarotpointcounterback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@Getter
public class GameResponse extends Game{
    private int id;
    private Timestamp timestamp;
}

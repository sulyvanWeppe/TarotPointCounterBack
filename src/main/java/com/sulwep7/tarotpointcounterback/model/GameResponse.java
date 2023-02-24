package com.sulwep7.tarotpointcounterback.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@Getter
public class GameResponse extends Game{
    private String uuid;
    private Timestamp timestamp;
}

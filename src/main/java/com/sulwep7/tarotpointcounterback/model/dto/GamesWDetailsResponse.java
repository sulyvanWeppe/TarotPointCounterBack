package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class GamesWDetailsResponse {
    @JsonProperty("games")
    private List<GameWDetailsResponse> gameWDetailsResponseList = new ArrayList<>();
}


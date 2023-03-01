package com.sulwep7.tarotpointcounterback.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class GamesWDetailsResponse {
    @JsonProperty("games")
    private List<GameWDetailsResponse> gameWDetailsResponseList;
}


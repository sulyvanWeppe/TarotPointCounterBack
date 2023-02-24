package com.sulwep7.tarotpointcounterback.mapper;

import com.sulwep7.tarotpointcounterback.model.PlayerScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlayersScoreMapper {

    List<PlayerScore> getPlayersScoreOfGame(@Param("GAME_ID")int gameId);
    void insertPlayerScore(@Param("GAME_ID")int gameId, @Param("PLAYER_NAME")String playerName, @Param("PLAYER_SCORE")int playerScore);

    void updatePlayerScore(@Param("GAME_ID")int gameId, @Param("PLAYER_NAME")String playerName, @Param("PLAYER_SCORE")int playerScore);
}

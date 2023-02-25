package com.sulwep7.tarotpointcounterback.mapper;

import com.sulwep7.tarotpointcounterback.model.PlayerScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.UUID;

@Mapper
public interface PlayersScoreMapper {

    List<PlayerScore> getPlayersScoreOfGame(@Param("GAME_UUID")String gameUuid);
    void insertPlayerScore(@Param("GAME_UUID")String gameUuid, @Param("PLAYER_NAME")String playerName, @Param("PLAYER_SCORE")int playerScore);

    void updatePlayerScore(@Param("GAME_UUID") String gameUuid, @Param("PLAYER_NAME")String playerName, @Param("PLAYER_SCORE")int playerScore);

    void deletePlayerScore(@Param("GAME_UUID") String gameUuid, @Param("PLAYER_NAME")String playerName);
}

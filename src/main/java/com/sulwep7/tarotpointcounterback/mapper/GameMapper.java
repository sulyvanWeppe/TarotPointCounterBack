package com.sulwep7.tarotpointcounterback.mapper;

import com.sulwep7.tarotpointcounterback.model.entity.Game;
import com.sulwep7.tarotpointcounterback.model.entity.GameWDetails;
import jdk.jshell.spi.ExecutionControlProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface GameMapper {

    List<Game> getAllGames();

    void insertGame(@Param("UUID") String uuid, @Param("TIMESTAMP")Timestamp timestamp, @Param("NR_PLAYERS")int nrPlayers) throws Exception;

    List<GameWDetails> getAllGamesWDetails();
}

package com.sulwep7.tarotpointcounterback.mapper;

import com.sulwep7.tarotpointcounterback.model.Game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface GameMapper {

    List<Game> getAllGames();

    void insertGame(@Param("TIMESTAMP")Timestamp timestamp, @Param("NR_PLAYERS")int nrPlayers);
}

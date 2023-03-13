package com.sulwep7.tarotpointcounterback.service;

import com.sulwep7.tarotpointcounterback.mapper.PlayersScoreMapper;
import com.sulwep7.tarotpointcounterback.model.entity.PlayerScore;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePatchRequest;
import com.sulwep7.tarotpointcounterback.model.dto.PlayerScorePostRequest;
import com.sulwep7.tarotpointcounterback.model.exception.DataStoringException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
@Setter
@Slf4j
public class PlayersScoreService {
    @Autowired
    private PlayersScoreMapper playersScoreMapper;

    public void insertPlayerScore(String gameUuid, String playerName, int playerScore) throws DataStoringException {
        try {
            log.info("Insert score {} for {}",playerScore,playerName);
            playersScoreMapper.insertPlayerScore(gameUuid,playerName,playerScore);
        } catch(Exception e) {
            log.error("Error inserting new entry in playersScore table : [{}]",e.getMessage());
            throw new DataStoringException("Error inserting new entry in playersScore table : "+e.getMessage());
        }
    }

    public void insertPlayersScore(String gameUuid, List<PlayerScorePostRequest> playerScorePostRequestList) throws DataStoringException{
        log.info("Insert scores for game {}",gameUuid);
        for(int i=0; i<playerScorePostRequestList.size(); i++) {
            try {
                PlayerScorePostRequest currentPlayer = playerScorePostRequestList.get(i);
                insertPlayerScore(gameUuid,currentPlayer.getPlayerName(),currentPlayer.getPlayerScore());
            } catch (Exception e) {
                log.error("Error inserting score of {}",playerScorePostRequestList.get(i).getPlayerName());
                for(int j=0; j<i; j++) {
                    log.info("Remove score of {}",playerScorePostRequestList.get(j).getPlayerName());
                    try {
                        playersScoreMapper.deletePlayerScore(gameUuid,playerScorePostRequestList.get(j).getPlayerName());
                    } catch (Exception eRollback) {
                        log.error("Error when trying to rollback score creation for player {}",playerScorePostRequestList.get(j).getPlayerName());
                        throw new DataStoringException("Error when trying to rollback score creation for player "+playerScorePostRequestList.get(j).getPlayerName());
                    }
                }

                throw new DataStoringException("Error updating scores of players for gameId "+gameUuid);
            }
        }
    }

    public void updatePlayerScore(String gameUuid, String playerName, int playerScore) throws DataStoringException{
        try {
            log.info("Update {}'s score to {}",playerName,playerScore);
            playersScoreMapper.updatePlayerScore(gameUuid,playerName,playerScore);
        } catch(Exception e) {
            log.error("Error updating score of {} for game {}",playerName,gameUuid);
            throw new DataStoringException("Error trying to update score for player "+playerName);
        }
    }

    public void updatePlayersScore(String gameUuid, List<PlayerScorePatchRequest> playerScorePatchRequestList) throws DataStoringException {
        //Save current scores in case a rollback is needed
        log.info("Update scores for game {}",gameUuid);
        List<PlayerScore> previousScores = playersScoreMapper.getPlayersScoreOfGame(gameUuid);

        for(int i=0; i<playerScorePatchRequestList.size(); i++) {
            try {
                PlayerScorePatchRequest currentPlayer = playerScorePatchRequestList.get(i);
                updatePlayerScore(gameUuid,currentPlayer.getPlayerName(),currentPlayer.getPlayerScore());
            } catch (Exception e) {
                log.error("Error updating score of {}",playerScorePatchRequestList.get(i).getPlayerName());
                for(int j=0; j<i; j++) {
                    log.info("Reset score of {} to {}",previousScores.get(j).getName(),previousScores.get(j).getScore());
                    try {
                        updatePlayerScore(gameUuid,previousScores.get(j).getName(),previousScores.get(j).getScore());
                    } catch (Exception eRollback) {
                        log.error("Error when trying to rollback score update for player {}",previousScores.get(j).getName());
                        throw new DataStoringException("Error when trying to rollback score update for player "+previousScores.get(j).getName());
                    }
                }

                throw new DataStoringException("Error updating scores of players for gameId "+gameUuid);
            }
        }
    }
}

select @gameUuid := UUID();

insert into game(uuid,timestamp,nrPlayers) values(@gameUuid,current_timestamp,4);

insert into players_score(gameUuid,playerName,playerScore) values(@gameUuid,'player1',10);
insert into players_score(gameUuid,playerName,playerScore) values(@gameUuid,'player2',20);
insert into players_score(gameUuid,playerName,playerScore) values(@gameUuid,'player3',30);
insert into players_score(gameUuid,playerName,playerScore) values(@gameUuid,'player4',40);

commit;
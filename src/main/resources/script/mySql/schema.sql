CREATE TABLE game(
	id int NOT NULL AUTO_INCREMENT,
	timestamp timestamp NOT NULL,
	nrPlayers int NOT NULL,
	PRIMARY KEY (id)
);


CREATE TABLE players_score (
	gameId int,
	playerName VARCHAR(100) NOT NULL,
	playerScore int NOT NULL,
	PRIMARY KEY(gameId,playerName),
	FOREIGN KEY (gameId) references game(id)
);
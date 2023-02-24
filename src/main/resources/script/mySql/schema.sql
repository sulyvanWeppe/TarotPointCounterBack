CREATE TABLE game(
	uuid VARCHAR(36) NOT NULL,
	timestamp timestamp NOT NULL,
	nrPlayers int NOT NULL,
	PRIMARY KEY (uuid)
);


CREATE TABLE players_score (
	gameUuid VARCHAR(36),
	playerName VARCHAR(100) NOT NULL,
	playerScore int NOT NULL,
	PRIMARY KEY(gameUuid,playerName),
	FOREIGN KEY (gameUuid) references game(uuid)
);
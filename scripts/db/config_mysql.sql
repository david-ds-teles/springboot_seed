USE spring; 

ALTER USER 'root' IDENTIFIED WITH mysql_native_password BY 'seed';

flush privileges;

CREATE TABLE IF NOT EXISTS account (
	id int NOT NULL AUTO_INCREMENT, 
	email varchar(255),
	provider varchar(255),
	updatedAt datetime,
    PRIMARY KEY(id)
);
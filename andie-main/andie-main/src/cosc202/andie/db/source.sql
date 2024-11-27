use baoonragwrzg9rs1hbv6;

drop table if exists users;
drop table if exists images;
drop table if exists userPreferences;

CREATE TABLE users (
  username VARCHAR(25) PRIMARY KEY,
  password VARCHAR(50) NOT NULL
);

CREATE TABLE images (
	imageID INT PRIMARY KEY,
  image BLOB NOT NULL,
  username VARCHAR(25),
  FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE userPreferences (
	theme VARCHAR(25),
  language VARCHAR(25),
  username VARCHAR(25),
  FOREIGN KEY (username) REFERENCES users(username)
);
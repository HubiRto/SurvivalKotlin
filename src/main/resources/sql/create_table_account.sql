CREATE TABLE IF NOT EXISTS account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_uuid VARCHAR (255) NOT NULL,
    player_name VARCHAR (255) NOT NULL,
    money DOUBLE NOT NULL
);
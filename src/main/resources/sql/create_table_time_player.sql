CREATE TABLE IF NOT EXISTS time_player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_uuid VARCHAR (255) NOT NULL,
    today_time LONG,
    collected_rewards INT,
    received_all BOOLEAN
);
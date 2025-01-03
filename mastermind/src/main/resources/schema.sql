CREATE TABLE games (
    id INT AUTO_INCREMENT PRIMARY KEY,
    number_combination VARCHAR(255) NOT NULL,
    attempts_left INT NOT NULL,
    is_game_over BOOLEAN NOT NULL,
    difficulty VARCHAR(1) CHECK (difficulty IN ('1', '2', '3')),
    score INT NOT NULL
);

CREATE TABLE game_response (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_input VARCHAR(255) NOT NULL,
    response VARCHAR(255) NOT NULL,
    attempts_left INT NOT NULL,
    http_status VARCHAR(255),
    score_deduction BIGINT NOT NULL,
    total_score BIGINT NOT NULL,
    game_id INT NOT NULL,
    FOREIGN KEY (game_id) REFERENCES games(id)
);
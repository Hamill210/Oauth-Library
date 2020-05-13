CREATE TABLE IF NOT EXISTS token
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    token_type VARCHAR(64),
    token      VARCHAR(64)
);

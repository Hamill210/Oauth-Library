DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS token;


CREATE TABLE token
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    token_type VARCHAR(64),
    token      VARCHAR(64)
);

CREATE TABLE user
(
    id        BIGINT PRIMARY KEY AUTO_INCREMENT,
    github_id VARCHAR(64),
    login     VARCHAR(64),
    name      VARCHAR(64),
    token     INT REFERENCES token (id)
);

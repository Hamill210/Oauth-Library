package com.codesquad.oauthgithublibrary.oauth.github;

import javafx.scene.effect.Bloom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TokenRepository {

    private static final Logger logger = LoggerFactory.getLogger(TokenRepository.class);

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TokenRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

//    public Token findById() {
//        String sql = "SELECT * FROM token WHERE id = ?";
//        Token token = jdbcTemplate.query()
//    }

    public void insertToken(Token token) {
        String sql = "INSERT INTO token (token_type, token) VALUES (:token_type, :token)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", token.getId())
                .addValue("token_type", token.getTokenType())
                .addValue("token", token.getToken());
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }
}

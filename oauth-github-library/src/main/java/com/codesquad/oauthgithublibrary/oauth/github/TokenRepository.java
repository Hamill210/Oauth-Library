package com.codesquad.oauthgithublibrary.oauth.github;

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

    public void insertToken(Token token) {
        String sql = "INSERT INTO token (token_type, token) VALUES (:token_type, :token)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", token.getId())
                .addValue("token_type", token.getTokenType())
                .addValue("token", token.getToken());
        namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    public Token findTokenObjectByAccessToken(String accessToken) {
        String sql = "SELECT * FROM token t WHERE t.token = ?";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Token>() {
            @Override
            public Token extractData(ResultSet rs) throws SQLException, DataAccessException {
                Token token = new Token();
                while (rs.next()) {
                    token.setId(rs.getLong("id"));
                    token.setTokenType(rs.getString("token_type"));
                    token.setToken(rs.getString("token"));
                }
                return token;
            }
        }, accessToken);
    }


}

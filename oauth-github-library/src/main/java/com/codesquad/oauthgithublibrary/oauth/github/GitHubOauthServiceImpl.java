package com.codesquad.oauthgithublibrary.oauth.github;

import com.codesquad.oauthgithublibrary.auth.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@Service
public class GitHubOauthServiceImpl implements GitHubOauthService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubOauthController.class);
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    private final String URL = "https://github.com/login/oauth/access_token";
    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;

    public GitHubOauthServiceImpl(TokenRepository tokenRepository, JwtService jwtService) {
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    public GitHubTokenInfo getAccessToken(String code) {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        Map<String, String> header = new HashMap<>();
        header.put("Accept", "application/json");
        headers.setAll(header);

        MultiValueMap<String, String> bodies = new LinkedMultiValueMap<>();
        Map<String, String> body = new HashMap<>();
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("code", code);
        bodies.setAll(body);

        HttpEntity<?> request = new HttpEntity<>(bodies, headers);
        ResponseEntity<?> response = new RestTemplate().postForEntity(URL, request, GitHubTokenInfo.class);
        return (GitHubTokenInfo) response.getBody();
    }

    @Transactional
    @Override
    public void login(HttpServletRequest request, HttpServletResponse response, String url, String authorizationCode) throws IOException {
        GitHubTokenInfo gitHubTokenInfo = getAccessToken(authorizationCode);
        logger.info("##### Access Token Type: {}, Access Token: {}",gitHubTokenInfo.getTokenType(), gitHubTokenInfo.getAccessToken());

        Token token = new Token(gitHubTokenInfo.getTokenType(), gitHubTokenInfo.getAccessToken());
        tokenRepository.insertToken(token);

        this.getUserData(request, response, url, token.getToken());

    }

    public void getUserData(HttpServletRequest request, HttpServletResponse response, String url, String accessToken) {
        Token token = tokenRepository.findTokenObjectByAccessToken(accessToken);
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            HttpEntity<?> entity = new HttpEntity<>(header);

            String githubOfficialUrl = "https://api.github.com/user";
            UriComponents sendAccessTokenUrl = UriComponentsBuilder.fromHttpUrl(githubOfficialUrl + "?access_token=" + token.getToken()).build();

            // 이 한줄의 코드로 API를 호출해 MAP 타입으로 전달 받는다
            ResponseEntity<Map> resultMap = restTemplate.exchange(sendAccessTokenUrl.toString(), HttpMethod.GET, entity, Map.class);

            User user = new User(
                    null,
                    resultMap.getBody().get("id").toString(),
                    resultMap.getBody().get("login").toString(),
                    resultMap.getBody().get("name").toString());

            String jwt = jwtService.makeJwt(user.getNickname(), user.getName());
            this.sendUserCookies(response, jwt, url);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.info("##### HttpErrorException: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("##### Exception: {}", e.getMessage());
        }
    }

    public void sendUserCookies(HttpServletResponse response, String jwt, String url) throws IOException {
        Cookie cookie = new Cookie("token", jwt);
        response.addCookie(cookie);
        response.sendRedirect(url);
    }
}

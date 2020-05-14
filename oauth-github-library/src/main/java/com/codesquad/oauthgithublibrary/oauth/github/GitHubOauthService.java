package com.codesquad.oauthgithublibrary.oauth.github;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GitHubOauthService {

    void login(String authorizationCode, HttpServletRequest request, HttpServletResponse response) throws IOException;
}

package com.codesquad.oauthgithublibrary.oauth.github;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private Long id;
    private String githubId;
    private String login;
    private String name;

    public User(Long id, String githubId, String login, String name) {
        this.id = id;
        this.githubId = githubId;
        this.login = login;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGithubId() { return githubId; }

    public void setGithubId(String githubId) { this.githubId = githubId; }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

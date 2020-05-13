package com.codesquad.oauthgithublibrary.oauth.github;

import org.springframework.data.annotation.Id;

public class User {

    @Id
    private Long id;
    private String githubId;
    private String nickname;
    private String name;

    public User() {

    }

    public User(Long id, String githubId, String nickname, String name) {
        this.id = id;
        this.githubId = githubId;
        this.nickname = nickname;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGithubId() {
        return githubId;
    }

    public void setGithubId(String githubId) {
        this.githubId = githubId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", githubId='" + githubId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

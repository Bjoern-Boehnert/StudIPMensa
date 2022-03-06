package com.bboehnert.studipmensa.view.login;

import com.bboehnert.studipmensa.models.user.User;

public class LoginResult {
    private final User user;
    private final int responseCode;

    public LoginResult(User user, int responseCode) {
        this.user = user;
        this.responseCode = responseCode;
    }

    public User getUser() {
        return user;
    }

    public int getResponseCode() {
        return responseCode;
    }
}

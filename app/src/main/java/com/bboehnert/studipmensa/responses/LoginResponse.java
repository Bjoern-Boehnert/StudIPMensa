package com.bboehnert.studipmensa.responses;

import com.bboehnert.studipmensa.models.user.User;

import java.util.Map;

public class LoginResponse implements Response<User> {

    private String user_id;
    private Map<String, String> name;
    private String avatar_original;

    @Override
    public User getResponse() {
        return new User(user_id, name, avatar_original);
    }
}

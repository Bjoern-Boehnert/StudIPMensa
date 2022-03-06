package com.bboehnert.studipmensa.models.user;

import java.util.Map;

public class User {

    private String id;
    private Map<String, String> name;
    private final String imagePath;

    public User(String id, Map<String, String> name, String imagePath) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
    }

    public String getUserId() {
        return id;
    }

    public Map<String, String> getName() {
        return name;
    }

    public String getImagePath() {
        return imagePath;
    }
}
package com.bboehnert.studipmensa;

public interface LocalStorage {
    void setPassword(String value);

    void setUsername(String value);

    String getPassword();

    String getUsername();
}



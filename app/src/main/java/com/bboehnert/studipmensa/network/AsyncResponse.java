package com.bboehnert.studipmensa.network;

public interface AsyncResponse {
    void processFinish(String output);

    void closeProgressDialog();

    void showProgressDialog();
}

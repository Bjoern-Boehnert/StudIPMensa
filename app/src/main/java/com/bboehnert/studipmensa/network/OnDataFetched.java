package com.bboehnert.studipmensa.network;

public interface OnDataFetched {
    void showProgressBar();

    void hideProgressBar();

    void setData(String result);
}

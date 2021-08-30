package com.bboehnert.studipmensa.network;

public interface OnDataFetched {
    void showProgress();

    void hideProgress();

    void setData(String result);
}

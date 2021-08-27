package com.bboehnert.studipmensa.network;

public interface iOnDataFetched {
    void showProgressBar();

    void hideProgressBar();

    void setDataInPageWithResult(String result);
}

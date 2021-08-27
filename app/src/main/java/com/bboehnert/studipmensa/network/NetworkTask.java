package com.bboehnert.studipmensa.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkTask implements CustomCallable<String> {

    private final OnDataFetched listener;
    private final String address;
    private final String cookieValue;

    public NetworkTask(OnDataFetched onDataFetchedListener, String address, String cookieValue) {
        this.listener = onDataFetchedListener;
        this.address = address;
        this.cookieValue = cookieValue;
    }

    @Override
    public String call() {
        return downloadJSON(address, cookieValue);
    }

    @Override
    public void setUiForLoading() {
        listener.showProgressBar();
    }

    @Override
    public void setDataAfterLoading(String result) {
        listener.setData(result);
        listener.hideProgressBar();
    }

    private String downloadJSON(String address, String cookieValue) {

        HttpURLConnection connection = null;
        BufferedReader buffredReader = null;

        try {
            URL url = new URL(address);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty(
                    "Cookie",
                    String.format("Seminar_Session=%s;", cookieValue));

            // Connection aufbauen
            connection.connect();

            InputStream stream = connection.getInputStream();
            buffredReader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();
            String line;
            // Solange Zeilen vorhanden sind aus dem Buffer schreiben
            while ((line = buffredReader.readLine()) != null) {
                buffer.append(line + "\n");
                Log.d("Response: ", "> " + line);
            }

            return buffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (buffredReader != null) {
                    buffredReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

}


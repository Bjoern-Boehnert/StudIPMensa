package com.bboehnert.studipmensa.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

class NetworkTask implements CustomCallable<String> {

    private final OnDataFetched listener;
    private final String address;
    private final String username;
    private final String password;

    public NetworkTask(OnDataFetched onDataFetchedListener, String address, String username, String password) {
        this.listener = onDataFetchedListener;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    @Override
    public String call() {
        return downloadJSON(address, username, password);
    }

    @Override
    public void setUiForLoading() {
        listener.showProgress();
    }

    @Override
    public void setDataAfterLoading(String result) {
        listener.setData(result);
        listener.hideProgress();
    }

    private String downloadJSON(String address, String username, String password) {

        HttpURLConnection connection = null;
        BufferedReader buffredReader = null;

        try {
            URL url = new URL(address);

            connection = (HttpURLConnection) url.openConnection();

            // Auth Header
            String auth = username + ":" + password;
            String encoded = Base64.getEncoder().encodeToString((auth).getBytes(StandardCharsets.UTF_8));
            connection.setRequestProperty("Authorization", "Basic " + encoded);

            // Connection aufbauen
            connection.connect();

            InputStream stream = connection.getInputStream();
            buffredReader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder builder = new StringBuilder();
            String line;
            // Solange Zeilen vorhanden sind aus dem Buffer schreiben
            while ((line = buffredReader.readLine()) != null) {
                builder.append(line).append("\n");
                Log.d("Response: ", "> " + line);
            }

            return builder.toString();

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


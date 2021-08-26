package com.bboehnert.studipmensa.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public final class ConnectionHelper {

    public static final String API_BASE_Address = "https://elearning.uni-oldenburg.de/api.php/";
    public static final String MENSA_Address = API_BASE_Address + "mensa/today";
    public static final String MENSA_Tomorrow_Address = API_BASE_Address + "mensa/tomorrow";

    public final static void downloadJsonContent(String address,
                                                 String cookieValue,
                                                 com.bboehnert.studipmensa.network.AsyncResponse delegate) {

        JSONNetworkOperation asyncTask = new JSONNetworkOperation(delegate);
        asyncTask.execute(address, cookieValue);
    }

    // Checking wether the network is connected
    public final static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private static class JSONNetworkOperation extends AsyncTask<String, Double, String> {

        private com.bboehnert.studipmensa.network.AsyncResponse delegate;

        public JSONNetworkOperation(com.bboehnert.studipmensa.network.AsyncResponse delegate) {
            this.delegate = delegate;
        }

        protected void onPreExecute() {
            delegate.showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection = null;
            BufferedReader buffredReader = null;

            try {
                URL url = new URL(strings[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty(
                        "Cookie",
                        String.format("Seminar_Session=%s;", strings[1]));

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

        @Override
        protected void onPostExecute(String jsonObject) {
            delegate.closeProgressDialog();
            delegate.processFinish(jsonObject);
        }

    }
}

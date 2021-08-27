package com.bboehnert.studipmensa.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ConnectionHelper {

    public static final String API_BASE_Address = "https://elearning.uni-oldenburg.de/api.php/";
    public static final String MENSA_Address = API_BASE_Address + "mensa/today";
    public static final String MENSA_Tomorrow_Address = API_BASE_Address + "mensa/tomorrow";

    public final static void downloadJsonContent(String address,
                                                 String cookieValue,
                                                 iOnDataFetched delegate) {

        TaskRunner asyncTask = new TaskRunner();
        asyncTask.executeAsync(new NetworkTask(delegate, address, cookieValue));
    }

    // Checking wether the network is connected
    public final static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private static class TaskRunner {

        private final Handler handler = new Handler(Looper.getMainLooper());
        private final Executor executor = Executors.newCachedThreadPool();

        public <R> void executeAsync(CustomCallable<R> callable) {
            try {
                callable.setUiForLoading();
                executor.execute(new RunnableTask<R>(handler, callable));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public static class RunnableTask<R> implements Runnable {
            private final Handler handler;
            private final CustomCallable<R> callable;

            public RunnableTask(Handler handler, CustomCallable<R> callable) {
                this.handler = handler;
                this.callable = callable;
            }

            @Override
            public void run() {
                try {
                    final R result = callable.call();
                    handler.post(new RunnableTaskForHandler(callable, result));
                } catch (Exception e) {
                    e.printStackTrace();
                    ;
                }
            }
        }

        public static class RunnableTaskForHandler<R> implements Runnable {

            private CustomCallable<R> callable;
            private R result;

            public RunnableTaskForHandler(CustomCallable<R> callable, R result) {
                this.callable = callable;
                this.result = result;
            }

            @Override
            public void run() {
                callable.setDataAfterLoading(result);
            }
        }
    }
}

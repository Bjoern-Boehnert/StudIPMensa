package com.bboehnert.studipmensa.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ConnectionHelper {

    public static final String API_BASE_Address = "https://elearning.uni-oldenburg.de/api.php/";
    public static final String MENSA_Address = API_BASE_Address + "mensa/today";
    public static final String MENSA_Tomorrow_Address = API_BASE_Address + "mensa/tomorrow";

    public static void downloadJsonContent(String address,
                                           String cookieValue,
                                           OnDataFetched delegate) {

        TaskRunner asyncTask = new TaskRunner();
        asyncTask.executeAsync(new NetworkTask(delegate, address, cookieValue));
    }

    // Prüfen ob der Nutzer mit dem Internet vernbunden ist
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    // Liefert die Mensadresse für ein bestimmten Tag
    public static String getMensaPlanAddress(Date date) {
        Log.d("Message", API_BASE_Address + "mensa/" + date.getTime()/1000);
        return API_BASE_Address + "mensa/" + date.getTime() / 1000;
    }

    private static class TaskRunner {

        private final Handler handler = new Handler(Looper.getMainLooper());
        private final Executor executor = Executors.newCachedThreadPool();

        private <R> void executeAsync(CustomCallable<R> callable) {
            try {

                // UI setzen für den Progressbar
                callable.setUiForLoading();

                // Ausführen des neuen Thread
                executor.execute(new RunnableTask<>(handler, callable));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private static class RunnableTask<R> implements Runnable {
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
                }
            }
        }

        private static class RunnableTaskForHandler<R> implements Runnable {

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

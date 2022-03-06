package com.bboehnert.studipmensa.network;

import android.content.Context;
import android.net.ConnectivityManager;

public final class ConnectionHelper {

    // Prüfen ob der Nutzer mit dem Internet vernbunden ist
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}

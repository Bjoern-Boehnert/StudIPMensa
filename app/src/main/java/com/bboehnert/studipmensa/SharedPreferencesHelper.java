package com.bboehnert.studipmensa;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesHelper {

    private final Context context;
    private SharedPreferences sharedPref;

    public SharedPreferencesHelper(Context context) {
        this.context = context;
        sharedPref = context.getSharedPreferences(
                "SharedPreferencesFile",
                Context.MODE_PRIVATE);
    }

    public String getSeminarSession() {
        return sharedPref.getString(
                context.getString(R.string.last_seminar_session),
                null);
    }

    public void setSeminarSession(String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.last_seminar_session), value);
        editor.apply();
    }

}

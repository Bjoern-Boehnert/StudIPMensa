package com.bboehnert.studipmensa;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

public class SharedPreferencesHelper {

    private final Context context;
    private SharedPreferences sharedPref;

    public SharedPreferencesHelper(Context context) {

        String masterKeyAlias;
        this.context = context;
        try {

            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            sharedPref = EncryptedSharedPreferences.create(
                    "SharedPreferencesFile",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPassword() {
        return sharedPref.getString(
                context.getString(R.string.password),
                null);
    }

    public String getUsername() {
        return sharedPref.getString(
                context.getString(R.string.username),
                null);
    }

    public void setPassword(String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.password), value);
        editor.apply();
    }

    public void setUsername(String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.username), value);
        editor.apply();
    }

}

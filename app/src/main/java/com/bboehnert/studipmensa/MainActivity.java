package com.bboehnert.studipmensa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bboehnert.studipmensa.network.AsyncResponse;
import com.bboehnert.studipmensa.network.ConnectionHelper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    // Controls
    private EditText seminarTokenText;
    private TextView errorMessageText;
    private RadioButton tomorrow;

    private ProgressDialog progressDialog;
    private SharedPreferencesHelper pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tomorrow = findViewById(R.id.foodTomorrowRadio);
        errorMessageText = findViewById(R.id.errorMessageText);
        seminarTokenText = findViewById(R.id.seminarTokenText);

        // Letzten Seminarschlüssel ins Textfeld eintragen
        pref = new SharedPreferencesHelper(this);
        seminarTokenText.setText(pref.getSeminarSession());

        // Fortschrittsbalken fürs Herunterladen
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);

    }

    @Override
    public void processFinish(String output) {

        if (output == null) {
            // Connection Fehler oder Falsche Route
            errorMessageText.setText("Seminar Cookie ist falsch!\nBitte erneut eingeben");
            return;
        }

        // Cookie in Shared Preferences abspeichern
        pref.setSeminarSession(seminarTokenText.getText().toString());

        // Errortext zurücksetzen
        errorMessageText.setText(null);

        // Erfolgreiche Verbindung - Daten anzeigen
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        intent.putExtra("jsonString", output);
        startActivity(intent);
    }

    @Override
    public void closeProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showProgressDialog() {
        progressDialog.show();
    }

    public void onLogin(View view) {
        if (!ConnectionHelper.isNetworkConnected(this)) {
            errorMessageText.setText("Internet nicht verbunden");
            return;
        }

        String address = null;
        if (tomorrow.isChecked()) {
            address = ConnectionHelper.MENSA_Tomorrow_Address;
        } else {
            address = ConnectionHelper.MENSA_Address;
        }

        // Trigger download
        ConnectionHelper.downloadJsonContent(address,
                seminarTokenText.getText().toString(),
                this);
    }

    public void helpButtonClieck(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info);
        dialog.show();
    }
}
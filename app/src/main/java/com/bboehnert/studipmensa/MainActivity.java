package com.bboehnert.studipmensa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.bboehnert.studipmensa.network.OnDataFetched;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnDataFetched {

    // Controls
    private TextInputEditText seminarTokenText;
    private RadioButton tomorrow;

    private ProgressDialog progressDialog;
    private SharedPreferencesHelper pref;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tomorrow = findViewById(R.id.foodTomorrowRadio);
        seminarTokenText = findViewById(R.id.seminarTokenText);

        // Letzten Seminarschlüssel ins Textfeld eintragen
        pref = new SharedPreferencesHelper(this);
        seminarTokenText.setText(pref.getSeminarSession());

        // Fortschrittsbalken fürs Herunterladen
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);

        toast = Toast.makeText(
                this,
                "Internet nicht verbunden",
                Toast.LENGTH_SHORT);
    }

    public void onLogin(View view) {
        if (!ConnectionHelper.isNetworkConnected(this)) {
            toast.show();
            seminarTokenText.setError(null);
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

    public void helpButtonClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info);
        dialog.show();
    }

    @Override
    public void showProgressBar() {
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setData(String output) {

        if (output == null) {
            // Connection Fehler oder Falsche Route
            seminarTokenText.setError("Seminar Cookie ist falsch!\nBitte erneut eingeben");
            return;
        }

        // Cookie in Shared Preferences abspeichern
        pref.setSeminarSession(seminarTokenText.getText().toString());

        // Errortext zurücksetzen
        seminarTokenText.setError(null);

        // Erfolgreiche Verbindung - Daten anzeigen
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        intent.putExtra("jsonString", output);
        startActivity(intent);

    }
}
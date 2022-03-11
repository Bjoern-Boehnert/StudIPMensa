package com.bboehnert.studipmensa.view.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.SharedPreferencesHelper;
import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.bboehnert.studipmensa.view.mensa.FoodActivity;
import com.bboehnert.studipmensa.viewModels.AuthViewModel;
import com.bboehnert.studipmensa.viewModels.MensaViewModel;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    // Controls
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private ProgressDialog progressDialog;

    @Inject
    public SharedPreferencesHelper prefs;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        setContentView(R.layout.activity_main);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        usernameText.setText(prefs.getUsername());
        passwordText.setText(prefs.getPassword());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);
    }

    public void onLogin(View view) {

        if (!ConnectionHelper.isNetworkConnected(this)) {
            showToast("Internet nicht verbunden");
            return;
        }
        this.progressDialog.show();

        authViewModel.login(
                usernameText.getText().toString(),
                passwordText.getText().toString()
        ).observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(LoginResult loginResult) {
                progressDialog.dismiss();
                if (loginResult.getResponseCode() == 200) {
                    // Erfolgreich angemeldet
                    loadMensaView();
                } else if (loginResult.getResponseCode() == 401) {
                    // Falsche Logindaten
                    passwordText.getText().clear();
                    usernameText.setError("Login Daten falsch");
                } else {
                    // Undefinierter Error
                    showToast("Es ist was schief gelaufen!");
                }
            }
        });
    }

    public void helpButtonClick(View view) {
        loadHelpView();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadMensaView() {
        // Activity öffnen
        Intent intent = new Intent(LoginActivity.this, FoodActivity.class);
        startActivity(intent);
    }

    private void loadHelpView() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info);
        dialog.show();
    }
}
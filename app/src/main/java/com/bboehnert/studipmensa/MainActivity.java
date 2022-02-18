package com.bboehnert.studipmensa;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.io.Serializable;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Contract.View {

    // Controls
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private ProgressDialog progressDialog;

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new Presenter(this);
        LocalStorage storage = new SharedPreferencesHelper(this);
        presenter.storeCredentials(storage);

        usernameText = findViewById(R.id.usernameText);
        passwordText = findViewById(R.id.passwordText);

        usernameText.setText(storage.getUsername());
        passwordText.setText(storage.getPassword());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);
    }

    public void onLogin(View view) {
        if (!ConnectionHelper.isNetworkConnected(this)) {
            showToast("Internet nicht verbunden");
            return;
        }
        // Eingabe validieren

        presenter.connect(
                ConnectionHelper.MENSA_Address,
                usernameText.getText().toString(),
                passwordText.getText().toString()
        );
    }

    public void helpButtonClick(View view) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.info);
        dialog.show();
    }

    @Override
    public void displayFood(List<Contract.Model> foodLocationsList) {
        showToast("Mensaplan erfolgreich abgefragt!");

        // Activity öffnen
        Intent intent = new Intent(MainActivity.this, FoodActivity.class);
        intent.putExtra("foodList", (Serializable) foodLocationsList);
        startActivity(intent);
    }

    @Override
    public void showNoMensaPlan(String message) {
        showToast(message);
    }

    @Override
    public void showJSONParsingError(String message) {
        showToast(message);
    }

    @Override
    public void showOnNetworkError(String message) {
        usernameText.setError(message);
        passwordText.getText().clear();
    }

    @Override
    public void showProgressbar() {
        progressDialog.show();
    }

    @Override
    public void hideProgressbar() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
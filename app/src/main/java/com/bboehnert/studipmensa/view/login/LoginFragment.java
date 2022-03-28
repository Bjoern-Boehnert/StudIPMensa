package com.bboehnert.studipmensa.view.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.SharedPreferencesHelper;
import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.bboehnert.studipmensa.viewModels.AuthViewModel;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends Fragment {

    // Controls
    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private ProgressDialog progressDialog;

    @Inject
    public SharedPreferencesHelper prefs;
    private AuthViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AuthViewModel.class);

        usernameText = v.findViewById(R.id.usernameText);
        passwordText = v.findViewById(R.id.passwordText);
        usernameText.setText(prefs.getUsername());
        passwordText.setText(prefs.getPassword());

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);

        Button loginButton = v.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v1 -> onLogin());

        Button infoButton = v.findViewById(R.id.infoButton);
        infoButton.setOnClickListener(v1 -> onInfo());

        return v;
    }

    private void onLogin() {

        if (!ConnectionHelper.isNetworkConnected(getActivity())) {
            showToast("Internet nicht verbunden");
            return;
        }
        this.progressDialog.show();

        viewModel.login(
                usernameText.getText().toString(),
                passwordText.getText().toString()
        ).observe(getViewLifecycleOwner(), new Observer<LoginResult>() {
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

    private void onInfo() {
        loadHelpView();        // Activity öffnen
//        Intent intent = new Intent(LoginActivity.this, FoodActivity.class);
//        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void loadMensaView() {
        // Activity öffnen
//        Intent intent = new Intent(LoginActivity.this, FoodActivity.class);
//        startActivity(intent);
    }

    private void loadHelpView() {
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.info);
        dialog.show();
    }

}
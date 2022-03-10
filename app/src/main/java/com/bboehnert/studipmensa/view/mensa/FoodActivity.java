package com.bboehnert.studipmensa.view.mensa;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.viewModels.MensaViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodActivity extends AppCompatActivity {

    private FoodListViewFragment foodlistFragment;
    private NoFoodFragment noFoodFragement;

    @Inject
    public MensaViewModel mensaViewModel;
    private TextView currentDate;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaplan);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Bitte warten");
        progressDialog.setCancelable(false);

        triggerDownloadRequest();

        if (savedInstanceState == null) {
            foodlistFragment = new FoodListViewFragment();
            noFoodFragement = new NoFoodFragment();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, foodlistFragment, null)
                    .add(R.id.fragment_container_view, noFoodFragement, null)
                    .hide(noFoodFragement)
                    .commit();
        }

        currentDate = findViewById(R.id.dateText);
        TextView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setText(String.format("DL: %s",
                getDateString(
                        mensaViewModel.setCalendar(0).getValue().getTime(),
                        "dd.MM.yyyy HH:mm"
                )));
    }

    private String getDateString(Date date, String pattern) {
        return new SimpleDateFormat(
                pattern,
                Locale.GERMANY).format(date);
    }

    private void switchFragment(Fragment hide, Fragment show) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(hide)
                .show(show)
                .commit();
    }

    public void getPreviousDay(View view) {
        setCurrentDate(-1);
        triggerDownloadRequest();

    }

    public void getNextDay(View view) {
        setCurrentDate(1);
        triggerDownloadRequest();
    }

    private void setCurrentDate(int add) {
        mensaViewModel.setCalendar(add).observe(this, new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                currentDate.setText(
                        getDateString(
                                calendar.getTime(),
                                "EEEE \t dd.MM.yyyy"
                        ));

            }
        });
    }

    private void displayFood(List<FoodGroupDisplayable> foodLocationsList) {
        foodlistFragment.setNewItems(foodLocationsList);

        switchFragment(noFoodFragement, foodlistFragment);
    }

    private void showNoMensaPlan(String message) {
        noFoodFragement.setText(message);

        switchFragment(foodlistFragment, noFoodFragement);
    }

    private void triggerDownloadRequest() {
        progressDialog.show();
        mensaViewModel.getMensaMenu().observe(this, new Observer<List<FoodGroupDisplayable>>() {
            @Override
            public void onChanged(List<FoodGroupDisplayable> foodGroupDisplayables) {
                if (foodGroupDisplayables == null) {
                    showNoMensaPlan(getText(R.string.no_mensa_message).toString());
                } else {
                    displayFood(foodGroupDisplayables);
                }
                progressDialog.dismiss();
            }

        });
    }

    public void getPrice(View view) {
        String priceText = String.format("Preis: %s €", this.foodlistFragment.getSelectionPrice());
        Toast.makeText(this, priceText, Toast.LENGTH_SHORT).show();
    }
}
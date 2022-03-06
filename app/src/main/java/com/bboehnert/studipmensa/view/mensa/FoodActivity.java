package com.bboehnert.studipmensa.view.mensa;

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
import com.bboehnert.studipmensa.responses.MensaResponse;
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

    private final Calendar calender = Calendar.getInstance();
    private TextView currentDate;
    private List<FoodGroupDisplayable> foodlist = new ArrayList<>();

    @Inject
    public MensaViewModel mensaViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaplan);

        triggerDownloadRequest(0);

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

        // Erstelle Download TimeStamp
        TextView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setText(String.format("DL: %s",
                getDateString(
                        calender.getTime(),
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
        triggerDownloadRequest(-1);
    }

    public void getNextDay(View view) {
        triggerDownloadRequest(1);
    }

    private void displayFood(List<FoodGroupDisplayable> foodLocationsList) {
        foodlistFragment.setNewItems(foodLocationsList);
        currentDate.setText(
                getDateString(
                        calender.getTime(),
                        "EEEE \t dd.MM.yyyy"
                ));

        // Fragement tauschen
        switchFragment(noFoodFragement, foodlistFragment);
    }

    private void showNoMensaPlan(String message) {
        noFoodFragement.setText(message);
        currentDate.setText(
                getDateString(
                        calender.getTime(),
                        "EEEE \t dd.MM.yyyy"
                ));
        // Lade Fragment "Kein Mensaplan"
        switchFragment(foodlistFragment, noFoodFragement);
    }

    private void triggerDownloadRequest(int add) {
        calender.add(Calendar.DATE, add);
        long day = getMensaPlanDay(calender.getTime());
        getFood(day);
    }

    private void getFood(long day) {
        this.mensaViewModel.getMensaMenu(day).observe(this, new Observer<MensaResponse>() {
            @Override
            public void onChanged(MensaResponse mensaResponse) {

                // Auch nochmal auf Login prüfen? Bei 401 zur Login Page?
                if (mensaResponse == null) {
                    showNoMensaPlan(getText(R.string.no_mensa_message).toString());
                } else {
                    //Mensaplan?
                    foodlist.clear();
                    foodlist.add(mensaResponse.getResponse().getUhlhornweg());
                    foodlist.add(mensaResponse.getResponse().getWechloy());
                    displayFood(foodlist);
                }
            }
        });
    }

    public void getPrice(View view) {
        String priceText = String.format("Preis: %s €", this.foodlistFragment.getSelectionPrice());
        Toast.makeText(this, priceText, Toast.LENGTH_SHORT).show();
    }

    private long getMensaPlanDay(Date date) {
        return date.getTime() / 1000;
    }
}
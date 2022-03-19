package com.bboehnert.studipmensa.view.mensa;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bboehnert.studipmensa.DateFormatHelper;
import com.bboehnert.studipmensa.MensaAction;
import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.viewModels.MensaViewModel;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodActivity extends AppCompatActivity {

    private FoodListViewFragment foodListFragment;
    private NoFoodFragment noFoodFragment;

    private MensaViewModel mensaViewModel;
    private Toast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mensaViewModel = new ViewModelProvider(this).get(MensaViewModel.class);

        setContentView(R.layout.activity_mensaplan);
        toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);

        if (savedInstanceState == null) {
            foodListFragment = new FoodListViewFragment();
            noFoodFragment = new NoFoodFragment();

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, foodListFragment, null)
                    .add(R.id.fragment_container_view, noFoodFragment, null)
                    .hide(noFoodFragment)
                    .commit();
        }

        String downloadDateString = DateFormatHelper.getDateString(
                Calendar.getInstance().getTime(),
                DateFormatHelper.DOWNLOAD_TIMESTAMP_FORMAT);

        TextView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setText(String.format("DL: %s", downloadDateString));

        mensaViewModel.getCalender().observe(this, new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                mensaViewModel.setMensaMenu();
            }
        });

        mensaViewModel.getMensaMenu().observe(this, new Observer<List<FoodGroupDisplayable>>() {
            @Override
            public void onChanged(List<FoodGroupDisplayable> foodGroupDisplayables) {
                if (foodGroupDisplayables == null) {
                    showNoMensaPlan(getText(R.string.no_mensa_message).toString());
                } else {
                    displayFood(foodGroupDisplayables);
                }
            }

        });
        mensaViewModel.getAction().observe(this, new Observer<MensaAction>() {
            @Override
            public void onChanged(MensaAction mensaAction) {
                if (mensaAction == MensaAction.GET_PRICE) {
                    showPrice();
                }
            }
        });

    }

    private void switchFragment(Fragment hide, Fragment show) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(hide)
                .show(show)
                .commit();
    }

    private void displayFood(List<FoodGroupDisplayable> foodLocationsList) {
        foodListFragment.setNewItems(foodLocationsList);
        switchFragment(noFoodFragment, foodListFragment);
    }

    private void showNoMensaPlan(String message) {
        foodListFragment.setNewItems(null);
        noFoodFragment.setText(message);
        switchFragment(foodListFragment, noFoodFragment);
    }

    private void showPrice() {
        toast.cancel();
        String priceText = String.format("Preis: %s €", this.foodListFragment.getSelectionPrice());
        toast.setText(priceText);
        toast.show();
    }

}
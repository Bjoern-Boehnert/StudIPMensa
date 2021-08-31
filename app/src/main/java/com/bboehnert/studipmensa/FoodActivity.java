package com.bboehnert.studipmensa;

import android.os.Bundle;
import android.widget.TextView;

import com.bboehnert.studipmensa.view.FoodDialogFragment;
import com.bboehnert.studipmensa.view.FoodListViewFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class FoodActivity extends AppCompatActivity implements FoodDialogFragment.DialogListener {

    /*
    Wichtig zu merken ist hier der Fragment Lifecycle zum Activity Lifecycle.
    Erst wenn On OnStart in der Activity aufgerufen wird kann auf die initialiserten Felder von
    Fragment zugegriffen werden: https://miro.medium.com/max/694/1*ALMDBkuAAZ28BJ2abmvniA.png
    */
    private List<Contract.Model> foodlist;
    private FoodListViewFragment foodlistFragment;
    private FoodDialogFragment foodDialogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        Serializable result = getIntent().getSerializableExtra("foodList");
        foodlist = (List<Contract.Model>) result;

        TextView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setText(formattedDate());
    }

    @Override
    protected void onStart() {
        super.onStart();

        foodlistFragment = (FoodListViewFragment) getSupportFragmentManager().getFragments().get(0);
        foodlistFragment.setFoodList(foodlist);

        foodDialogFragment = new FoodDialogFragment();

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, foodDialogFragment, null)
                .hide(foodDialogFragment)
                .commit();

    }

    private String formattedDate() {
        String dateString = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.GERMANY).format(Calendar.getInstance().getTime());

        return String.format("DL: %s", dateString);
    }

    private void showFragment(Fragment hide, Fragment show) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .hide(hide)
                .show(show)
                .commit();
    }

    @Override
    public void displayChild(int location, int item) {
        foodDialogFragment.setFoodItem(foodlist.get(location).getAllFood().get(item));

        showFragment(foodlistFragment, foodDialogFragment);
    }

    @Override
    public void displayListView() {
        showFragment(foodDialogFragment, foodlistFragment);
    }
}

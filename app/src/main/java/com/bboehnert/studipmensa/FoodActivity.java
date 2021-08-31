package com.bboehnert.studipmensa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bboehnert.studipmensa.network.ConnectionHelper;
import com.bboehnert.studipmensa.view.CustomListAdapter;
import com.bboehnert.studipmensa.view.FoodListViewFragment;
import com.bboehnert.studipmensa.view.NoFoodFragment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class FoodActivity extends AppCompatActivity implements Contract.View {

    /*
    Wichtig zu merken ist hier der Fragment Lifecycle zum Activity Lifecycle.
    Erst wenn On OnStart in der Activity aufgerufen wird kann auf die initialiserten Felder von
    Fragment zugegriffen werden: https://miro.medium.com/max/694/1*ALMDBkuAAZ28BJ2abmvniA.png
    */

    private FoodListViewFragment foodlistFragment;
    private NoFoodFragment noFoodFragement;

    private List<Contract.Model> foodlist;

    private Calendar calender = Calendar.getInstance();
    private CustomListAdapter customListAdapter;
    private TextView currentDate;
    private Contract.Presenter presenter;
    private SharedPreferencesHelper pref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaplan);

        presenter = new Presenter(this);
        pref = new SharedPreferencesHelper(this);
        currentDate = findViewById(R.id.dateText);

        Serializable result = getIntent().getSerializableExtra("foodList");
        foodlist = (List<Contract.Model>) result;

        TextView timeStamp = findViewById(R.id.timeStamp);
        String dateText = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.GERMANY).format(calender.getTime());

        timeStamp.setText(String.format("DL: %s", dateText));

        currentDate.setText(formattedDate(calender.getTime()));
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Layout enthält nur eine View
        foodlistFragment = (FoodListViewFragment) getSupportFragmentManager().getFragments().get(0);
        customListAdapter = new CustomListAdapter(this, foodlist);
        foodlistFragment.getExpandableListView().setAdapter(customListAdapter);

        noFoodFragement = new NoFoodFragment();
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, noFoodFragement, null)
                .hide(noFoodFragement)
                .commit();

    }

    private String formattedDate(Date date) {
        return new SimpleDateFormat(
                "EEEE \t dd.MM.yyyy",
                Locale.GERMANY).format(date);
    }

    private void switchFragement(Fragment hide, Fragment show) {
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

    @Override
    public void displayFood(List<Contract.Model> foodLocationsList) {
        customListAdapter.setNewItems(foodLocationsList);
        currentDate.setText(formattedDate(calender.getTime()));

        // Fragement tauschen
        switchFragement(noFoodFragement, foodlistFragment);
    }

    @Override
    public void showNoMensaPlan(String message) {
        Log.d("Mensa", message);
        customListAdapter.setNewItems(null);
        currentDate.setText(formattedDate(calender.getTime()));

        // Lade Fragment "Kein Mensaplan"
        switchFragement(foodlistFragment, noFoodFragement);
    }

    @Override
    public void showJSONParsingError(String message) {
    }

    @Override
    public void showOnNetworkError(String message) {
    }

    @Override
    public void showProgressbar() {
    }

    @Override
    public void hideProgressbar() {
    }

    @Override
    public void saveSeminarCookie(String seminarSession) {
    }

    private void triggerDownloadRequest(int add) {
        calender.add(Calendar.DATE, add);
        String url = ConnectionHelper.getMensaPlanAddress(calender.getTime());
        presenter.connect(url, pref.getSeminarSession());
    }
}

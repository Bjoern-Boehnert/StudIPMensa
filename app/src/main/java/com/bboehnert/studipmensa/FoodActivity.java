package com.bboehnert.studipmensa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bboehnert.studipmensa.entity.FoodLocation;
import com.bboehnert.studipmensa.entity.MensaHelper;
import com.bboehnert.studipmensa.view.CustomListAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;

public class FoodActivity extends Activity {

    private ExpandableListView foodListView;
    private TextView timeStamp;

    private com.bboehnert.studipmensa.SharedPreferencesHelper pref;
    private List<FoodLocation> foodLocationsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        timeStamp = findViewById(R.id.timeStamp);

        Date time = Calendar.getInstance().getTime();
        String dateString = new SimpleDateFormat("yyyy-MM-dd: HH-mm").format(time);
        timeStamp.setText("DL: " + dateString);


        foodListView = findViewById(R.id.foodListView);
        pref = new com.bboehnert.studipmensa.SharedPreferencesHelper(this);
        String result = getIntent().getStringExtra("jsonString");

        try {
            foodLocationsList = getFoodList(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        displayFood(foodLocationsList);
    }

    private void displayFood(List<FoodLocation> foodLocationsList) {
        CustomListAdapter customListAdapter = new CustomListAdapter(this, foodLocationsList);
        foodListView.setAdapter(customListAdapter);
    }

    private List<FoodLocation> getFoodList(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(MensaHelper.parseKeyNames(result));
        return MensaHelper.getFoodList(jsonObject);
    }
}

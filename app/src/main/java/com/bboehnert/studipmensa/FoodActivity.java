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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;

public class FoodActivity extends Activity {

    private ExpandableListView foodListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

        foodListView = findViewById(R.id.foodListView);

        TextView timeStamp = findViewById(R.id.timeStamp);
        String dateString = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.GERMANY).format(Calendar.getInstance().getTime());

        timeStamp.setText(String.format("DL: %s", dateString));

        String result = getIntent().getStringExtra("jsonString");

        try {
            List<FoodLocation> foodLocationsList = null;
            if (MensaHelper.hasMensaPlan(result)) {
                foodLocationsList = getFoodList(result);
            } else {
                // Leere Liste anzeigen
                foodLocationsList = new ArrayList<>();
            }

            displayFood(foodLocationsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void displayFood(List<FoodLocation> foodLocationsList) {
        CustomListAdapter customListAdapter = new CustomListAdapter(this, foodLocationsList);
        foodListView.setAdapter(customListAdapter);
    }

    // Parsen des JSON-Strings
    private List<FoodLocation> getFoodList(String result) throws JSONException {
        JSONObject jsonObject = new JSONObject(MensaHelper.parseKeyNames(result));
        return MensaHelper.getFoodList(jsonObject);
    }
}

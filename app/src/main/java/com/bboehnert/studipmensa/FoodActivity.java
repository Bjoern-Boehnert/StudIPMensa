package com.bboehnert.studipmensa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bboehnert.studipmensa.view.CustomListAdapter;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
        Serializable result = getIntent().getSerializableExtra("foodList");
        populateListView((List<Contract.Model>) result);

        TextView timeStamp = findViewById(R.id.timeStamp);
        timeStamp.setText(formattedDate());
    }

    private String formattedDate() {
        String dateString = new SimpleDateFormat(
                "dd.MM.yyyy HH:mm",
                Locale.GERMANY).format(Calendar.getInstance().getTime());

        return String.format("DL: %s", dateString);
    }

    private void populateListView(List<Contract.Model> foodLocationsList) {
        CustomListAdapter customListAdapter = new CustomListAdapter(this, foodLocationsList);
        foodListView.setAdapter(customListAdapter);
    }
}

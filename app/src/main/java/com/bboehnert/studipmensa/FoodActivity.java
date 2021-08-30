package com.bboehnert.studipmensa;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bboehnert.studipmensa.view.CustomListAdapter;
import com.bboehnert.studipmensa.view.ExampleDialog;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class FoodActivity extends AppCompatActivity implements ExampleDialog.DialogListener {

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
        foodListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {

                openDialog();
                return true;
            }
        });
    }

    private void openDialog() {
        ExampleDialog dialog = new ExampleDialog();
        dialog.show(getSupportFragmentManager(), "Example Dialog");
    }

    @Override
    public void applyText(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

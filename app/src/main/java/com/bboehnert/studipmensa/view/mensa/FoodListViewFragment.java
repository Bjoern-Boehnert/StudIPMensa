package com.bboehnert.studipmensa.view.mensa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;

import java.util.ArrayList;
import java.util.List;

public class FoodListViewFragment extends Fragment {

    private CustomListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_expandablelist, container, false);

        ExpandableListView expandableListView = v.findViewById(R.id.foodListView);
        this.adapter = new CustomListAdapter(getContext(), new ArrayList<>());
        expandableListView.setAdapter(this.adapter);

        return v;
    }

    public void setNewItems(List<FoodGroupDisplayable> foodList) {
        this.adapter.setNewItems(foodList);
    }

    public double getSelectionPrice() {
        return this.adapter.getSelectionPrice();
    }
}
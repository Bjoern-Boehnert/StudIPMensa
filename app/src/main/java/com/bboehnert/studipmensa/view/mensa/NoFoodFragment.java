package com.bboehnert.studipmensa.view.mensa;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bboehnert.studipmensa.R;

public class NoFoodFragment extends Fragment {

    private TextView noFoodTextView;

    public NoFoodFragment() {
        super(R.layout.fragment_nofood);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        this.noFoodTextView = (TextView) getView().findViewById(R.id.noFoodTextView);
    }

    public void setText(String text) {
        this.noFoodTextView.setText(text);
    }
}

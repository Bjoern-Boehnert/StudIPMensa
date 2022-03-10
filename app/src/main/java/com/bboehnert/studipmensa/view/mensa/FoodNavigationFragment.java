package com.bboehnert.studipmensa.view.mensa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bboehnert.studipmensa.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodNavigationFragment extends Fragment {

    private TextView dateView;
    private Button previousButton;
    private Button nextButton;
    private Button priceButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foodnavigation, container, false);

        dateView = v.findViewById(R.id.dateText);
        previousButton = v.findViewById(R.id.previousButton);
        nextButton = v.findViewById(R.id.nextButton);
        priceButton = v.findViewById(R.id.priceButton);

        return v;
    }


}

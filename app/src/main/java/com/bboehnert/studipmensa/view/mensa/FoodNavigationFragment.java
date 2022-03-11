package com.bboehnert.studipmensa.view.mensa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bboehnert.studipmensa.DateFormatHelper;
import com.bboehnert.studipmensa.MensaAction;
import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.viewModels.MensaViewModel;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FoodNavigationFragment extends Fragment {

    private MensaViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_foodnavigation, container, false);

        TextView dateView = v.findViewById(R.id.dateText);

        viewModel = new ViewModelProvider(getActivity()).get(MensaViewModel.class);
        viewModel.setAction(MensaAction.GET_CURRENT_DAY);
        viewModel.getCalender().observe(getViewLifecycleOwner(), new Observer<Calendar>() {
            @Override
            public void onChanged(Calendar calendar) {
                dateView.setText(
                        DateFormatHelper.getDateString(
                                calendar.getTime(),
                                DateFormatHelper.WEEKDAY_FORMAT)
                );
            }
        });
        Button previousButton = v.findViewById(R.id.previousButton);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setAction(MensaAction.GET_PREVIOUS_DAY);
            }
        });
        Button nextButton = v.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setAction(MensaAction.GET_NEXT_DAY);
            }
        });
        Button priceButton = v.findViewById(R.id.priceButton);
        priceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.setAction(MensaAction.GET_PRICE);
            }
        });

        return v;
    }
}

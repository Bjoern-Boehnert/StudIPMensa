package com.bboehnert.studipmensa.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.bboehnert.studipmensa.Contract;
import com.bboehnert.studipmensa.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FoodListViewFragment extends Fragment {


    private ExpandableListView expandableListView;
    private FoodDialogFragment.DialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.expandablelist_fragment, container, false);
        expandableListView = v.findViewById(R.id.foodListView);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition,
                                        long id) {

                listener.displayChild(groupPosition, childPosition);
                return true;
            }
        });
        return v;
    }

    public void setFoodList(List<Contract.Model> foodList) {
        CustomListAdapter customListAdapter = new CustomListAdapter(getContext(), foodList);
        expandableListView.setAdapter(customListAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FoodDialogFragment.DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Class must implement DialogListener interface");
        }
    }
}

package com.bboehnert.studipmensa.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.entity.FoodItemDisplayable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FoodDialogFragment extends Fragment {

    private TextView nameText, attributesText, priceText, typeText;

    public interface DialogListener {
        void displayChild(int location, int item);

        void displayListView();
    }

    private DialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_details_fragment, container, false);
        Button back = v.findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.displayListView();
            }
        });

        nameText = v.findViewById(R.id.item_details_nameText);
        attributesText = v.findViewById(R.id.item_details_attributesText);
        priceText = v.findViewById(R.id.item_details_priceText);
        typeText = v.findViewById(R.id.item_details_typeText);

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Class must implement DialogListener interface");
        }
    }

    public void setFoodItem(FoodItemDisplayable foodItemDisplayable) {

        StringBuilder sb = new StringBuilder();
        for (String s : foodItemDisplayable.getAttributes()) {
            sb.append(s);
            sb.append(",");
        }

        nameText.setText(String.format("Name: %s", foodItemDisplayable.getName()));
        attributesText.setText(String.format("Attribute: %s",sb.toString()));
        priceText.setText(String.format("Preis: %s €", foodItemDisplayable.getPrice()));
        typeText.setText(String.format("Typ: %s",foodItemDisplayable.getTypeName()));
    }
}

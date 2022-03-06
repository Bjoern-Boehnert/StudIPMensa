package com.bboehnert.studipmensa.view.mensa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import com.bboehnert.studipmensa.models.mensa.FoodGroupDisplayable;
import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.models.mensa.FoodItemDisplayable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

class CustomListAdapter extends BaseExpandableListAdapter {

    private List<FoodGroupDisplayable> listData;
    private final Context context;
    private int selectedGroup = -1;
    private int selectedChildCounter = 0;

    public CustomListAdapter(Context context, List<FoodGroupDisplayable> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getGroupCount() {
        return this.listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listData.get(groupPosition).getAllFood().size();
    }

    @Override
    public FoodGroupDisplayable getGroup(int groupPosition) {
        return this.listData.get(groupPosition);
    }

    @Override
    public FoodItemDisplayable getChild(int groupPosition,
                                        int childPosition) {

        List<FoodItemDisplayable> foodList = this.listData.get(groupPosition).getAllFood();

        if (foodList == null) {
            return null;
        }

        return foodList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition,
                           int childPosition) {

        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {

        FoodGroupDisplayable location = getGroup(groupPosition);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView textView = convertView.findViewById(R.id.list_group);
        textView.setText(location.getName());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        FoodItemDisplayable foodItem = getChild(groupPosition, childPosition);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

        TextView typeView = convertView.findViewById(R.id.list_item_label);
        typeView.setText(foodItem.getTypeName());

        TextView nameView = convertView.findViewById(R.id.list_item_body);
        nameView.setText(foodItem.getName());

        TextView priceView = convertView.findViewById(R.id.list_item_body2);

        String priceTag = foodItem.getPrice() + " €";
        priceView.setText(priceTag);

        TextView symbolView = convertView.findViewById(R.id.list_item_symbol);
        symbolView.setText(String.valueOf(foodItem.getTypeName().charAt(0)));

        CheckedTextView checkBox = convertView.findViewById(R.id.checkBox);
        // Sehr wichtig die checkbox schon zu initialisieren. Weil sonst die
        // Listitems wiederverwendet werden.
        // https://stackoverflow.com/questions/30516879/double-click-item-in-listview-android/30517563
        checkBox.setChecked(foodItem.isSelected());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedChildCounter == 0) {
                    selectedGroup = groupPosition;

                } else if (selectedGroup != groupPosition) {
                    // Noch selektierte Einträge aus anderer Gruppe vorhanden
                    return;
                }

                checkBox.setChecked(!foodItem.isSelected());
                foodItem.setSelected(!foodItem.isSelected());

                if (foodItem.isSelected()) {
                    selectedChildCounter++;
                } else {
                    selectedChildCounter--;
                }

            }
        });

        return convertView;
    }

    public void setNewItems(List<FoodGroupDisplayable> listData) {
        // Inhalt leeren bei null
        if (listData == null) {
            this.listData.clear();
        } else {
            this.listData = listData;
        }
        selectedGroup = -1;
        selectedChildCounter = 0;

        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition,
                                     int childPosition) {

        return true;
    }

    public double getSelectionPrice() {
        if (this.listData == null || selectedGroup == -1) return 0;
        double price = 0;
        for (FoodItemDisplayable displayable : this.listData.get(this.selectedGroup).getAllFood()) {
            if (displayable.isSelected()) {
                price += displayable.getPrice();
            }
        }

        return parseDoublePrecision(price);
    }

    private double parseDoublePrecision(double value) {
        return BigDecimal.valueOf(value)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
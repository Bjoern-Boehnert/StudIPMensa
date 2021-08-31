package com.bboehnert.studipmensa.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bboehnert.studipmensa.Contract;
import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.entity.FoodItemDisplayable;

import java.util.List;

public class CustomListAdapter extends BaseExpandableListAdapter {

    private List<Contract.Model> listData;
    private final Context context;

    public CustomListAdapter(Context context, List<Contract.Model> listData) {
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
    public Contract.Model getGroup(int groupPosition) {
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

        Contract.Model location = getGroup(groupPosition);
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

        return convertView;
    }

    public void setNewItems(List<Contract.Model> listData) {
        // Inhalt leeren bei null
        if (listData == null) {
            this.listData.clear();
        } else {
            this.listData = listData;
        }
        notifyDataSetChanged();
    }

    @Override
    public boolean isChildSelectable(int groupPosition,
                                     int childPosition) {

        return true;
    }

}
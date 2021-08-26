package com.bboehnert.studipmensa.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bboehnert.studipmensa.R;
import com.bboehnert.studipmensa.entity.FoodItem;
import com.bboehnert.studipmensa.entity.FoodLocation;

import java.util.List;

public class CustomListAdapter extends BaseExpandableListAdapter {

    private List<FoodLocation> listData;
    private Context context;

    public CustomListAdapter(Context context, List<FoodLocation> listData) {
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
    public FoodLocation getGroup(int groupPosition) {
        return this.listData.get(groupPosition);
    }

    @Override
    public FoodItem getChild(int groupPosition,
                             int childPosition) {

        List<FoodItem> foodList = this.listData.get(groupPosition).getAllFood();

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

        FoodLocation location = getGroup(groupPosition);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
        }

        TextView textView = convertView.findViewById(R.id.list_parent);
        textView.setText(location.getName());
        return convertView;

    }

    @Override
    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        FoodItem foodItem = getChild(groupPosition, childPosition);
        if (convertView == null) {

            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);

        }

        TextView textView = convertView.findViewById(R.id.list_child);
        textView.setText(foodItem.getName());
        TextView labelView = convertView.findViewById(R.id.list_child_label);
        labelView.setText(foodItem.getTypeName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition,
                                     int childPosition) {

        return true;
    }

}
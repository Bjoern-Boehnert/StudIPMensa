package com.bboehnert.studipmensa.models.mensa;

public interface FoodItemDisplayable {

    // Fooditem Felder
    String getName();

    double getPrice();

    String[] getAttributes();


    String getTypeName();

    boolean isSelected();

    void setSelected(boolean value);
}
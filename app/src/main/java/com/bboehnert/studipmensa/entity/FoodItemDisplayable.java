package com.bboehnert.studipmensa.entity;

import java.io.Serializable;

public interface FoodItemDisplayable extends Serializable {
    String getName();

    double getPrice();

    String[] getAttributes();

    String getTypeName();
}
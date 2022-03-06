package com.bboehnert.studipmensa.models.mensa;

import java.util.List;

public interface FoodGroupDisplayable {
    List<FoodItemDisplayable> getAllFood();

    String getName();
}

package com.bboehnert.studipmensa.models.mensa;

import com.google.gson.annotations.SerializedName;

public class MensaLocation {

    @SerializedName("2")
    private FoodGroupEntity uhlhornweg;

    @SerializedName("3")
    private FoodGroupEntity wechloy;

    public FoodGroupDisplayable getUhlhornweg() {
        if(uhlhornweg == null) return null;
        return new FoodGroup(uhlhornweg, "Uhlhornweg");
    }

    public FoodGroupDisplayable getWechloy() {
        if(wechloy == null) return null;
        return new FoodGroup(wechloy, "Wechloy");
    }
}
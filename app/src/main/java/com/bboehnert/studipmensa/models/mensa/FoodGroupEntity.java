package com.bboehnert.studipmensa.models.mensa;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class FoodGroupEntity {

    @SerializedName("Hauptgerichte")
    private List<FoodItemEntity> mainDishes;

    @SerializedName("Suppen")
    private List<FoodItemEntity> soups;

    @SerializedName("Beilagen")
    private List<FoodItemEntity> extras;

    @SerializedName("Gemüse")
    private List<FoodItemEntity> vegetable;

    @SerializedName("Salate")
    private List<FoodItemEntity> salads;

    @SerializedName("Desserts")
    private List<FoodItemEntity> desserts;

    @SerializedName("Veggie & Vegan")
    private List<FoodItemEntity> vegan;

    @SerializedName("Pasta")
    private List<FoodItemEntity> pasta;

    @SerializedName("Classic")
    private List<FoodItemEntity> classics;

    public List<FoodItemEntity> getMainDishes() {
        return mainDishes;
    }

    public List<FoodItemEntity> getSoups() {
        return soups;
    }

    public List<FoodItemEntity> getExtras() {
        return extras;
    }

    public List<FoodItemEntity> getVegetable() {
        return vegetable;
    }

    public List<FoodItemEntity> getSalads() {
        return salads;
    }

    public List<FoodItemEntity> getDesserts() {
        return desserts;
    }

    public List<FoodItemEntity> getVegan() {
        return vegan;
    }

    public List<FoodItemEntity> getPasta() {
        return pasta;
    }

    public List<FoodItemEntity> getClassics() {
        return classics;
    }
}
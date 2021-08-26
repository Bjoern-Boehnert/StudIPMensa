package com.bboehnert.studipmensa.entity;

import java.util.ArrayList;
import java.util.List;

public class FoodLocation {
    private String name;
    private List<FoodItem> totalFoodItems = null;

    private List<MainDish> mainDish;
    private List<Extra> extras;
    private List<Vegetable> vegetables;
    private List<Salad> salad;
    private List<Desert> dessert;
    private List<Veggie> veggie;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<MainDish> getMainDish() {
        return mainDish;
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public List<Vegetable> getVegetables() {
        return vegetables;
    }

    public List<Salad> getSalad() {
        return salad;
    }

    public List<Desert> getDessert() {
        return dessert;
    }

    public List<Veggie> getVeggie() {
        return veggie;
    }

    public List<FoodItem> getAllFood() {

        if (totalFoodItems != null) {
            return totalFoodItems;
        }

        totalFoodItems = new ArrayList<>();
        addFoodItems(mainDish);
        addFoodItems(extras);
        addFoodItems(vegetables);
        addFoodItems(salad);
        addFoodItems(dessert);
        addFoodItems(veggie);

        return totalFoodItems;
    }

    private void addFoodItems(List<? extends FoodItem> list) {
        if (list == null) return;
        totalFoodItems.addAll(list);
    }


}
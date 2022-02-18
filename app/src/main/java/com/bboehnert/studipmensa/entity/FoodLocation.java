package com.bboehnert.studipmensa.entity;

import com.bboehnert.studipmensa.Contract;

import java.util.ArrayList;
import java.util.List;

class FoodLocation implements Contract.Model {
    private String name;
    private List<FoodItemDisplayable> totalFoodItems = null;

    private List<MainDish> mainDish;
    private List<Extra> extras;
    private List<Vegetable> vegetables;
    private List<Salad> salad;
    private List<Desert> dessert;
    private List<Veggie> veggie;
    private List<Classic> classic;
    private List<Pasta> pasta;
    private List<Soup> soup;

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<FoodItemDisplayable> getAllFood() {

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
        addFoodItems(classic);
        addFoodItems(pasta);
        addFoodItems(soup);

        return totalFoodItems;
    }

    private void addFoodItems(List<? extends FoodItem> list) {
        if (list == null) return;
        totalFoodItems.addAll(list);
    }
}
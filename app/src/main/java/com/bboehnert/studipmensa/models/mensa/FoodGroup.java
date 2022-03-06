package com.bboehnert.studipmensa.models.mensa;

import java.util.ArrayList;
import java.util.List;

class FoodGroup implements FoodGroupDisplayable {

    private String name;
    private List<FoodItemDisplayable> totalFoodItems = new ArrayList<>();

    public FoodGroup(FoodGroupEntity entity, String name) {
        this.name = name;

        if (entity.getMainDishes() != null) {
            for (FoodItemEntity e : entity.getMainDishes()) {
                totalFoodItems.add(new FoodItem(e, "Hauptgericht"));
            }
        }
        if (entity.getClassics() != null) {
            for (FoodItemEntity e : entity.getClassics()) {
                totalFoodItems.add(new FoodItem(e, "Classic"));
            }
        }
        if (entity.getPasta() != null) {
            for (FoodItemEntity e : entity.getPasta()) {
                totalFoodItems.add(new FoodItem(e, "Pasta"));
            }
        }
        if (entity.getVegan() != null) {
            for (FoodItemEntity e : entity.getVegan()) {
                totalFoodItems.add(new FoodItem(e, "Vegan"));
            }
        }
        if (entity.getSoups() != null) {
            for (FoodItemEntity e : entity.getSoups()) {
                totalFoodItems.add(new FoodItem(e, "Suppe"));
            }
        }
        if (entity.getVegetable() != null) {
            for (FoodItemEntity e : entity.getVegetable()) {
                totalFoodItems.add(new FoodItem(e, "Gemüse"));
            }
        }
        if (entity.getExtras() != null) {
            for (FoodItemEntity e : entity.getExtras()) {
                totalFoodItems.add(new FoodItem(e, "Beilage"));
            }
        }
        if (entity.getSalads() != null) {
            for (FoodItemEntity e : entity.getSalads()) {
                totalFoodItems.add(new FoodItem(e, "Salat"));
            }
        }
        if (entity.getDesserts() != null) {
            for (FoodItemEntity e : entity.getDesserts()) {
                totalFoodItems.add(new FoodItem(e, "Dessert"));
            }
        }

    }

    @Override
    public List<FoodItemDisplayable> getAllFood() {
        return totalFoodItems;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
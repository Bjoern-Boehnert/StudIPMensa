package com.bboehnert.studipmensa.models.mensa;

class FoodItemEntity {
    private String name;
    private String[] attributes;
    private double price;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String[] getAttributes() {
        return attributes;
    }
}

package com.bboehnert.studipmensa.entity;

public abstract class FoodItem {

    // Für Googles GSON müssen die Json tags wie in dem original-File heißen
    private String name;
    private double price;
    private String[] attributes;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String[] getAttributes() {
        return attributes;
    }

    public abstract String getTypeName();
}

class MainDish extends FoodItem {

    @Override
    public String getTypeName() {
        return "Hauptgericht";
    }
}

class Extra extends FoodItem {

    @Override
    public String getTypeName() {
        return "Beilagen";
    }
}

class Vegetable extends FoodItem {

    @Override
    public String getTypeName() {
        return "Gemüse";
    }
}

class Salad extends FoodItem {

    @Override
    public String getTypeName() {
        return "Salat";
    }
}

class Desert extends FoodItem {

    @Override
    public String getTypeName() {
        return "Dessert";
    }
}

class Veggie extends FoodItem {

    @Override
    public String getTypeName() {
        return "Veggie & Vegan";
    }
}

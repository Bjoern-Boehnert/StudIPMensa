package com.bboehnert.studipmensa.entity;

abstract class FoodItem implements FoodItemDisplayable {

    // Für Googles GSON müssen die Json tags wie in dem original-File heißen
    private String name;
    private double price;
    private String[] attributes;
    private boolean selected;

    @Override
    public void setSelected(boolean value) {
        selected = value;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String[] getAttributes() {
        return attributes;
    }

    @Override
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
        return "Beilage";
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

class Soup extends FoodItem {

    @Override
    public String getTypeName() {
        return "Suppe";
    }
}

class Pasta extends FoodItem {

    @Override
    public String getTypeName() {
        return "Pasta";
    }
}

class Classic extends FoodItem {

    @Override
    public String getTypeName() {
        return "Classic";
    }
}
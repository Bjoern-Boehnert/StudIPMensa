package com.bboehnert.studipmensa.models.mensa;

class FoodItem implements FoodItemDisplayable {

    private FoodItemEntity entity;
    private String typename;
    private boolean selected;

    public FoodItem(FoodItemEntity entity, String typename) {
        this.entity = entity;
        this.typename = typename;
    }

    @Override
    public String getName() {
        return entity.getName();
    }

    @Override
    public double getPrice() {
        return entity.getPrice();
    }

    @Override
    public String[] getAttributes() {
        return entity.getAttributes();
    }

    @Override
    public String getTypeName() {
        return this.typename;
    }

    @Override
    public boolean isSelected() {
        return this.selected;
    }

    @Override
    public void setSelected(boolean value) {
        this.selected = value;
    }
}

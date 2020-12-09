package com.mealwith.Entity;

public class Ingredients {
    private int id;
    private int category_id;
    private int origin_id;
    private int unit_id;
    private String name;
    private Double price;
    private int temp_min;
    private int temp_max;
    private int shelf_life;
    private String picture;

    public Ingredients(int category_id, int origin_id, int unit_id, String name, double price, int temp_min, int temp_max, int shelf_life, String picture) {
        this.category_id = category_id;
        this.origin_id = origin_id;
        this.unit_id = unit_id;
        this.name = name;
        this.price = price;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.shelf_life = shelf_life;
        this.picture = picture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getOrigin_id() {
        return origin_id;
    }

    public void setOrigin_id(int origin_id) {
        this.origin_id = origin_id;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(int temp_min) {
        this.temp_min = temp_min;
    }

    public int getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(int temp_max) {
        this.temp_max = temp_max;
    }

    public int getShelf_life() {
        return shelf_life;
    }

    public void setShelf_life(int shelf_life) {
        this.shelf_life = shelf_life;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}

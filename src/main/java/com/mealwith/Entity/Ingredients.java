package com.mealwith.Entity;

import com.mealwith.DAO.CategoriesIngredientsDAO;
import com.mealwith.DAO.OriginDAO;
import com.mealwith.DAO.UnitDAO;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

public class Ingredients {
    private int id;
    private int category_id;
    private String category_name;
    private int origin_id;
    private String origin_name;
    private int unit_id;
    private String unit_name;
    private String name;
    private Double price;
    private int temp_min;
    private int temp_max;
    private int shelf_life;
    private String picture;
    private ImageView picture_Img;

    public Ingredients(int id,int category_id, int origin_id, int unit_id, String name, double price, int temp_min, int temp_max, int shelf_life, String picture,ImageView picture_Img) throws SQLException {

        this.id = id;

        this.category_id = category_id;
        CategoriesIngredientsDAO repoCatIngr = new CategoriesIngredientsDAO();
        this.category_name = repoCatIngr.GetNameByID(category_id);


        this.origin_id = origin_id;
        OriginDAO repoOrigin = new OriginDAO();
        this.origin_name = repoOrigin.GetCountryByID(origin_id);

        this.unit_id = unit_id;
        UnitDAO repoUnit = new UnitDAO();
        this.unit_name = repoUnit.GetTypeByID(unit_id);

        this.name = name;
        this.price = price;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.shelf_life = shelf_life;
        this.picture = picture;
        this.picture_Img = picture_Img;
        this.picture_Img.setFitWidth(80);
        this.picture_Img.setFitHeight(80);
        this.picture_Img.setPreserveRatio(true);
    }

    /**
     * Constructeur utilisant un jeu de paramètre très réduit, utilisé spécifiquement pour la recherche d'ingredient par leur nom, dans la barre de recherche du controlleur "Ingredients"
     * @param id ID de l'ingredient
     * @param name Nom de l'ingredient
     * @param category_id ID de la catégorie de l'ingredient
     */
    public Ingredients(int id, int category_id, String name,String picture) throws SQLException {
        this.id = id;
        this.name = name;
        this.picture = picture;
        this.category_id = category_id;
        CategoriesIngredientsDAO repoCatIngr = new CategoriesIngredientsDAO();
        this.category_name = repoCatIngr.GetNameByID(category_id);
    }

    public Ingredients(int category_id, int origin_id, int unit_id, String name, double price, int temp_min, int temp_max, int shelf_life, String picture){
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

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getOrigin_name() {
        return origin_name;
    }

    public void setOrigin_name(String origin_name) {
        this.origin_name = origin_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public ImageView getPicture_Img() {
        return picture_Img;
    }

    public void setPicture_Img(ImageView picture_Img) {
        this.picture_Img = picture_Img;
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

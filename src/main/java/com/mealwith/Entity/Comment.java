package com.mealwith.Entity;

import java.util.Date;

public class Comment {
    private Integer id;
    private Integer ingredientId;
    private Integer recipeId;
    private Integer userId;
    private java.sql.Date creationDate;
    private String content;

    public Comment(Integer id, Integer userId, Integer recipeId, Integer ingredientId, java.sql.Date creationDate, String content) {
        this.id = id;
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
        this.userId = userId;
        this.creationDate = creationDate;
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.sql.Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

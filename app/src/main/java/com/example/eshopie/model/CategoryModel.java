package com.example.eshopie.model;

public class CategoryModel {

    public String getCategoryIcon() {
        return CategoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        CategoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(String categoryIcon, String categoryName) {
        CategoryIcon = categoryIcon;
        this.categoryName = categoryName;
    }

    private String CategoryIcon;
    private String categoryName;
}

package com.example.eshopie.model;

public class CategoryModel {

    private String CategoryIcon;
    private String categoryName;

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

}

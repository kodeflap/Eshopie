package com.example.eshopie.model;

public class OrderItemModel {

    private int productImage;
    private int rating;
    private String productTitle;
    private String deliveryStatus;

    public OrderItemModel(int productImage, String productTitle, String deliveryStatus, int rating) {
        this.productImage = productImage;
        this.rating = rating;
        this.productTitle = productTitle;
        this.deliveryStatus = deliveryStatus;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}

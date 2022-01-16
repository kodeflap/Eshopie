package com.example.eshopie.model;

public class RewardModel {

    private String title;
    private String expiryDate;
    private String rewardBody;

    public RewardModel(String title, String expiryDate, String rewardBody) {
        this.title = title;
        this.expiryDate = expiryDate;
        this.rewardBody = rewardBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getRewardBody() {
        return rewardBody;
    }

    public void setRewardBody(String rewardBody) {
        this.rewardBody = rewardBody;
    }
}

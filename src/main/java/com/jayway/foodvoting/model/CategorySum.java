package com.jayway.foodvoting.model;

public class CategorySum {

    private String foodPick;
    private long votes;

    public CategorySum(){}

    public CategorySum(String foodPick, long votes) {
        this.foodPick = foodPick;
        this.votes = votes;
    }

    public String getFoodPick() {
        return foodPick;
    }

    public void setFoodPick(String foodPick) {
        this.foodPick = foodPick;
    }

    public long getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}

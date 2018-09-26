package com.jayway.foodvoting.model;

import java.util.Objects;

public class CategoryStatistics {
    private String category;
    private double co2e;
    private long numPortions;
    private double portionsPercent;

    public CategoryStatistics() {}

    public CategoryStatistics(String category, double co2e){
        this.category = category;
        this.co2e = co2e;
    }

    public CategoryStatistics(String category, double co2e, long numPortions) {
        this.category = category;
        this.co2e = co2e;
        this.numPortions = numPortions;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getCo2e() {
        return co2e;
    }

    public void setCo2e(double co2e) {
        this.co2e = co2e;
    }

    public double getPortionsPercent() {
        return portionsPercent;
    }

    public void setPortionsPercent(double portionsPercent) {
        this.portionsPercent = portionsPercent;
    }

    public long getNumPortions() {
        return numPortions;
    }

    public void setNumPortions(long numPortions) {
        this.numPortions = numPortions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryStatistics that = (CategoryStatistics) o;
        return Double.compare(that.co2e, co2e) == 0 &&
                numPortions == that.numPortions &&
                Double.compare(that.portionsPercent, portionsPercent) == 0 &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, co2e, numPortions, portionsPercent);
    }

    @Override
    public String toString() {
        return "CategoryStatistics{" +
                "category='" + category + '\'' +
                ", co2e=" + co2e +
                ", numPortions=" + numPortions +
                ", portionsPercent=" + portionsPercent +
                '}';
    }
}
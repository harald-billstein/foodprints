package com.jayway.foodvoting.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Statistics {

    // TODO Also add from and to (dates)?
    private double totalCo2e;
    private int totalPortions;
    private List<CategoryStatistics> categoryStatistics = new ArrayList<>();

    /**
     * Difference same period of time before the current one in CO2e.
     */
    private double difference;

    public Statistics() { }

    public Statistics(double totalCo2e, int totalPortions, List<CategoryStatistics> categoryStatistics) {
        this.totalCo2e = totalCo2e;
        this.totalPortions = totalPortions;
        this.categoryStatistics = categoryStatistics;
    }

    public double getTotalCo2e() {
        return totalCo2e;
    }

    public void setTotalCo2e(double totalCo2e) {
        this.totalCo2e = totalCo2e;
    }

    public List<CategoryStatistics> getCategoryStatistics() {
        return categoryStatistics;
    }

    public void setCategoryStatistics(List<CategoryStatistics> categoryStatistics) {
        this.categoryStatistics = categoryStatistics;
    }

    public double getDifference() {
        return difference;
    }

    public void setDifference(double difference) {
        this.difference = difference;
    }

    public int getTotalPortions() {
        return totalPortions;
    }

    public void setTotalPortions(int totalPortions) {
        this.totalPortions = totalPortions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Statistics that = (Statistics) o;
        return Double.compare(that.totalCo2e, totalCo2e) == 0 &&
                totalPortions == that.totalPortions &&
                Double.compare(that.difference, difference) == 0 &&
                Objects.equals(categoryStatistics, that.categoryStatistics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalCo2e, totalPortions, categoryStatistics, difference);
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalCo2e=" + totalCo2e +
                ", totalPortions=" + totalPortions +
                ", categoryStatistics=" + categoryStatistics +
                ", difference=" + difference +
                '}';
    }
}
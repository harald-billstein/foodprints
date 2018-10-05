package com.jayway.foodvoting.model;

public class EmissionPerMonthPerPortion {

  private int year;
  private int month;
  private double totalEmission;
  private int totalPortions;
  private double emissionPerPortion;

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public double getTotalEmission() {
    return totalEmission;
  }

  public void setTotalEmission(double totalEmission) {
    this.totalEmission = totalEmission;
  }

  public int getTotalPortions() {
    return totalPortions;
  }

  public void setTotalPortions(int totalPortions) {
    this.totalPortions = totalPortions;
  }

  public double getEmissionPerPortion() {
    emissionPerPortion = totalEmission / totalPortions;

    if (Double.isNaN(totalEmission / totalPortions)) {
      return 0;
    } else {
      return totalEmission / totalPortions;
    }
  }
}

package com.jayway.foodvoting.model;

public class EmissionPerMonthPerPortion {

  private int year;
  private int month;
  private double totalEmission;
  private int totalVotes;

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

  public int getTotalVotes() {
    return totalVotes;
  }

  public void setTotalVotes(int totalVotes) {
    this.totalVotes = totalVotes;
  }

  public double getEmissionPerPortion() {
    emissionPerPortion = totalEmission / totalVotes;

    if (Double.isNaN(emissionPerPortion)) {
      return 0;
    } else {

      return emissionPerPortion;
    }
  }

  public void setEmissionPerPortion(double emissionPerPortion) {
    this.emissionPerPortion = emissionPerPortion;
  }
}
package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.model.CategoryStatistics;
import com.jayway.foodvoting.model.Statistics;
import java.util.ArrayList;
import java.util.List;

public class StatisticsFactory {
  private double totalCo2e;
  private int totalPortions;
  private double difference;

  private double beefCo2e = 0;
  private double beefPortionsProcent = 0;
  private int beefNumPortions = 0;

  private double porkCo2e = 0;
  private double porkPortionsProcent = 0;
  private int porkNumPortions = 0;

  private double chickenCo2e = 0;
  private double chickenPortionsProcent = 0;
  private int chickenNumPortions = 0;

  private double fishCo2e = 0;
  private double fishPortionsProcent = 0;
  private int fishNumPortions = 0;

  private double vegetarianCo2e = 0;
  private double vegetarianPortionsProcent = 0;
  private int vegetarianNumPortions = 0;

  private double veganCo2e = 0;
  private double veganPortionsProcent = 0;
  private int veganNumPortions = 0;

  public double getTotalCo2e() {
    return totalCo2e;
  }

  public StatisticsFactory setTotalCo2e(double totalCo2e) {
    this.totalCo2e = totalCo2e;
    return this;
  }

  public int getTotalPortions() {
    return totalPortions;
  }

  public StatisticsFactory setTotalPortions(int totalPortions) {
    this.totalPortions = totalPortions;
    return this;
  }

  public double getDifference() {
    return difference;
  }

  public StatisticsFactory setDifference(double difference) {
    this.difference = difference;
    return this;
  }

  public double getBeefCo2e() {
    return beefCo2e;
  }

  public StatisticsFactory setBeefCo2e(double beefCo2e) {
    this.beefCo2e = beefCo2e;
    return this;
  }

  public double getBeefPortionsProcent() {
    return beefPortionsProcent;
  }

  public StatisticsFactory setBeefPortionsProcent(double beefPortionsProcent) {
    this.beefPortionsProcent = beefPortionsProcent;
    return this;
  }

  public int getBeefNumPortions() {
    return beefNumPortions;
  }

  public StatisticsFactory setBeefNumPortions(int beefNumPortions) {
    this.beefNumPortions = beefNumPortions;
    return this;
  }

  public double getPorkCo2e() {
    return porkCo2e;
  }

  public StatisticsFactory setPorkCo2e(double porkCo2e) {
    this.porkCo2e = porkCo2e;
    return this;
  }

  public double getPorkPortionsProcent() {
    return porkPortionsProcent;
  }

  public StatisticsFactory setPorkPortionsProcent(double porkPortionsProcent) {
    this.porkPortionsProcent = porkPortionsProcent;
    return this;
  }

  public int getPorkNumPortions() {
    return porkNumPortions;
  }

  public StatisticsFactory setPorkNumPortions(int porkNumPortions) {
    this.porkNumPortions = porkNumPortions;
    return this;
  }

  public double getChickenCo2e() {
    return chickenCo2e;
  }

  public StatisticsFactory setChickenCo2e(double chickenCo2e) {
    this.chickenCo2e = chickenCo2e;
    return this;
  }

  public double getChickenPortionsProcent() {
    return chickenPortionsProcent;
  }

  public StatisticsFactory setChickenPortionsProcent(double chickenPortionsProcent) {
    this.chickenPortionsProcent = chickenPortionsProcent;
    return this;
  }

  public int getChickenNumPortions() {
    return chickenNumPortions;
  }

  public StatisticsFactory setChickenNumPortions(int chickenNumPortions) {
    this.chickenNumPortions = chickenNumPortions;
    return this;
  }

  public double getFishCo2e() {
    return fishCo2e;
  }

  public StatisticsFactory setFishCo2e(double fishCo2e) {
    this.fishCo2e = fishCo2e;
    return this;
  }

  public double getFishPortionsProcent() {
    return fishPortionsProcent;
  }

  public StatisticsFactory setFishPortionsProcent(double fishPortionsProcent) {
    this.fishPortionsProcent = fishPortionsProcent;
    return this;
  }

  public int getFishNumPortions() {
    return fishNumPortions;
  }

  public StatisticsFactory setFishNumPortions(int fishNumPortions) {
    this.fishNumPortions = fishNumPortions;
    return this;
  }

  public double getVegetarianCo2e() {
    return vegetarianCo2e;
  }

  public StatisticsFactory setVegetarianCo2e(double vegetarianCo2e) {
    this.vegetarianCo2e = vegetarianCo2e;
    return this;
  }

  public double getVegetarianPortionsProcent() {
    return vegetarianPortionsProcent;
  }

  public StatisticsFactory setVegetarianPortionsProcent(double vegetarianPortionsProcent) {
    this.vegetarianPortionsProcent = vegetarianPortionsProcent;
    return this;
  }

  public int getVegetarianNumPortions() {
    return vegetarianNumPortions;
  }

  public StatisticsFactory setVegetarianNumPortions(int vegetarianNumPortions) {
    this.vegetarianNumPortions = vegetarianNumPortions;
    return this;
  }

  public double getVeganCo2e() {
    return veganCo2e;
  }

  public StatisticsFactory setVeganCo2e(double veganCo2e) {
    this.veganCo2e = veganCo2e;
    return this;
  }

  public double getVeganPortionsProcent() {
    return veganPortionsProcent;
  }

  public StatisticsFactory setVeganPortionsProcent(double veganPortionsProcent) {
    this.veganPortionsProcent = veganPortionsProcent;
    return this;
  }

  public int getVeganNumPortions() {
    return veganNumPortions;
  }

  public StatisticsFactory setVeganNumPortions(int veganNumPortions) {
    this.veganNumPortions = veganNumPortions;
    return this;
  }

  public Statistics build(){
    Statistics statistics = new Statistics();
    statistics.setDifference(difference);
    statistics.setTotalCo2e(totalCo2e);
    statistics.setTotalPortions(totalPortions);


    List<CategoryStatistics> categoryStatisticsList = new ArrayList<>();

    CategoryStatistics beef = new CategoryStatistics();
    beef.setCategory("BEEF");
    beef.setCo2e(this.beefCo2e);
    beef.setPortionsPercent(this.beefPortionsProcent);
    beef.setNumPortions(this.beefNumPortions);

    CategoryStatistics pork = new CategoryStatistics();
    pork.setCategory("PORK");
    pork.setCo2e(this.porkCo2e);
    pork.setPortionsPercent(this.porkPortionsProcent);
    pork.setNumPortions(this.porkNumPortions);

    CategoryStatistics chicken = new CategoryStatistics();
    chicken.setCategory("CHICKEN");
    chicken.setCo2e(this.chickenCo2e);
    chicken.setPortionsPercent(this.chickenPortionsProcent);
    chicken.setNumPortions(this.chickenNumPortions);

    CategoryStatistics fish = new CategoryStatistics();
    fish.setCategory("FISH");
    fish.setCo2e(this.fishCo2e);
    fish.setPortionsPercent(this.fishPortionsProcent);
    fish.setNumPortions(this.fishNumPortions);

    CategoryStatistics vegetarian = new CategoryStatistics();
    vegetarian.setCategory("VEGETARIAN");
    vegetarian.setCo2e(this.vegetarianCo2e);
    vegetarian.setPortionsPercent(this.vegetarianPortionsProcent);
    vegetarian.setNumPortions(this.vegetarianNumPortions);

    CategoryStatistics vegan = new CategoryStatistics();
    vegan.setCategory("VEGAN");
    vegan.setCo2e(this.veganCo2e);
    vegan.setPortionsPercent(this.veganPortionsProcent);
    vegan.setNumPortions(this.veganNumPortions);

    categoryStatisticsList.add(beef);
    categoryStatisticsList.add(pork);
    categoryStatisticsList.add(chicken);
    categoryStatisticsList.add(fish);
    categoryStatisticsList.add(vegetarian);
    categoryStatisticsList.add(vegan);

    statistics.setCategoryStatistics(categoryStatisticsList);

    return statistics;
  }

}

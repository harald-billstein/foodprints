package com.jayway.foodvoting.model.yelp;

import java.util.List;

public class YelpRestaurants {

  private List<Business> businesses;
  private int total;
  private Region region;

  public List<Business> getBusinesses() {
    return businesses;
  }

  public void setBusinesses(List<Business> businesses) {
    this.businesses = businesses;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }
}



package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.integrationtest.RestaurantsResource;
import com.jayway.foodvoting.model.Restaurant;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterTest {

  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;
  private static final int MIN_ADDRESS_LENGTH = 5;

  private List<Restaurant> unfilteredRestaurants;

  @Before
  public void setupRestaurantLists() {

    try {
      unfilteredRestaurants = RestaurantsResource.getRestaurantResource();
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  @Test
  public void filterWeedingCheck() {
    List<Restaurant> filtered = RestaurantFilter.restaurantGradeFilter(unfilteredRestaurants);
    Assert.assertTrue(unfilteredRestaurants.size() >= filtered.size());
  }

  @Test
  public void filterResultChecker() {
    List<Restaurant> filtered = RestaurantFilter.restaurantGradeFilter(unfilteredRestaurants);

    for (Restaurant restaurant : filtered) {
      Assert.assertTrue("Rating test failed at : " + restaurant.getName(),
          restaurant.getRating() > MIN_RATING);
      Assert.assertTrue("Vote count failed at : " + restaurant.getName(),
          restaurant.getReviewCount() > MIN_VOTES);
      Assert.assertTrue("Address not present at : " + restaurant.getName(),
          restaurant.getAddress().length() > MIN_ADDRESS_LENGTH);
    }
  }

  @Test
  public void filterNoMatchingRestaurantsChecker() {
    List<Restaurant> restaurants = new ArrayList<>();
    Assert.assertTrue(RestaurantFilter.restaurantGradeFilter(restaurants).size() <= 0);
  }
}

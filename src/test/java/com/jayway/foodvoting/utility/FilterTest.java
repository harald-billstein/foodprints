package com.jayway.foodvoting.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.foodvoting.integrationtest.RestaurantsJsonWrapper;
import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterTest {

  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;
  private static final int MIN_ADDRESS_LENGTH = 5;

  private Restaurants unfilteredRestaurants;

  @Before
  public void setupRestaurantLists() {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      unfilteredRestaurants = objectMapper
          .readValue(RestaurantsJsonWrapper.getRestaurantSuggestionsEndpointJSON(),
              Restaurants.class);
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  @Test
  public void filterWeedingCheck() {
    List<Business> filtered = RestaurantFilter.RestaurantGradeFilter(unfilteredRestaurants);
    Assert.assertTrue(unfilteredRestaurants.getBusinesses().size() >= filtered.size());
  }

  @Test
  public void filterResultChecker() {
    List<Business> filtered = RestaurantFilter.RestaurantGradeFilter(unfilteredRestaurants);

    for (Business business : filtered) {
      Assert.assertTrue("Rating test failed at : " + business.getName(),
          business.getRating() > MIN_RATING);
      Assert.assertTrue("Vote count failed at : " + business.getName(),
          business.getReview_count() > MIN_VOTES);
      Assert.assertTrue("Address not present at : " + business.getName(),
          business.getLocation().getAddress1().length() > MIN_ADDRESS_LENGTH);
    }
  }

  @Test
  public void filterNoMatchingRestaurantsChecker() {
    Restaurants restaurants = new Restaurants();
    ArrayList<Business> businesses = new ArrayList<>();
    restaurants.setBusinesses(businesses);

    Assert.assertNull(RestaurantFilter.RestaurantGradeFilter(restaurants));
  }


}

package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.model.Restaurant;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantFilter.class);
  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;
  private static final int MIN_ADDRESS_LENGTH = 5;

  public static List<Restaurant> restaurantGradeFilter(List<Restaurant> unFilteredRestaurants) {

    List<Restaurant> filteredRestaurants = new ArrayList<>();
    LOGGER.debug("RESTAURANT FILTER : RESTAURANTS FOUND : "
        + unFilteredRestaurants.size());

    for (Restaurant tempRestaurant : unFilteredRestaurants) {

      if (tempRestaurant.getRating() >= MIN_RATING
          && tempRestaurant.getAddress().length() >= MIN_ADDRESS_LENGTH
          && tempRestaurant.getReviewCount() >= MIN_VOTES) {
        LOGGER.debug("RESTAURANT PASSED INSPECTION : " + tempRestaurant.getName());
        filteredRestaurants.add(tempRestaurant);
      }
    }
    LOGGER.debug("RESTAURANT FILTER : RESTAURANTS QUALIFIES : " + filteredRestaurants.size());

    return filteredRestaurants;

  }
}

package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantFilter.class);
  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;
  private static final int MIN_ADDRESS_LENGTH = 5;

  public static List<Business> RestaurantGradeFilter(Restaurants unFilteredRestaurants) {

    List<Business> filteredRestaurants = new ArrayList<>();
    LOGGER.debug("RESTAURANT FILTER : RESTAURANTS FOUND : "
        + unFilteredRestaurants.getBusinesses().size());

    for (Business tempBusiness : unFilteredRestaurants.getBusinesses()) {
      if (tempBusiness.getRating() >= MIN_RATING
          && tempBusiness.getLocation().getAddress1().length() > MIN_ADDRESS_LENGTH
          && tempBusiness.getReview_count() > MIN_VOTES) {
        LOGGER.debug("RESTAURANT PASSED INSPECTION : " + tempBusiness.getName());
        filteredRestaurants.add(tempBusiness);
      }
    }
    LOGGER.debug("RESTAURANT FILTER : RESTAURANTS QUALIFIES : " + filteredRestaurants.size());

    if (filteredRestaurants.size() > 0) {
      return filteredRestaurants;
    } else {
      return null;
    }
  }
}

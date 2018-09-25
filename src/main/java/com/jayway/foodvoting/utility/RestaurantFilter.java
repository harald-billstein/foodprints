package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class RestaurantFilter {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantFilter.class);
  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;

  public static List<Business> RestaurantGradeFilter(Flux<Restaurants> unFilteredRestaurants) {

    List<Business> filteredRestaurants = new ArrayList<>();
    LOGGER.info("RESTAURANT FILTER : RESTAURANTS FOUND : "
        + unFilteredRestaurants.blockFirst().getBusinesses().size());

    for (Business tempBusiness : unFilteredRestaurants.blockFirst().getBusinesses()) {
      if (tempBusiness.getRating() >= MIN_RATING
          && tempBusiness.getLocation().getAddress1().length() > 0
          && tempBusiness.getReview_count() > MIN_VOTES) {
        LOGGER.info("RESTAURANT PASSED INSPECTION : " + tempBusiness.getName());
        filteredRestaurants.add(tempBusiness);
      }
    }
    LOGGER.info("RESTAURANT FILTER : RESTAURANTS QUALIFIES : " + filteredRestaurants.size());
    return filteredRestaurants;
  }
}

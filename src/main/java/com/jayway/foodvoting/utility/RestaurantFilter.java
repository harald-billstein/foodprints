package com.jayway.foodvoting.utility;

import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
import java.util.ArrayList;
import java.util.List;
import reactor.core.publisher.Flux;

public class RestaurantFilter {

  public static List<Business> RestaurantGradeFilter(Flux<Restaurants> unFilteredRestaurants) {

    List<Business> filteredRestaurants = new ArrayList<>();
    for (Business tempBusiness : unFilteredRestaurants.blockFirst().getBusinesses()) {
      if (tempBusiness.getRating() >= 4) {
        filteredRestaurants.add(tempBusiness);
      }
    }
    return filteredRestaurants;
  }
}

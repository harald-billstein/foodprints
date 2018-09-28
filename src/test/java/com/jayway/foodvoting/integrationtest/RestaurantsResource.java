package com.jayway.foodvoting.integrationtest;

import com.jayway.foodvoting.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsResource {

  public static List<Restaurant> getRestaurantResource() {

    List<Restaurant> restaurants = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      Restaurant restaurant = new Restaurant();
      restaurant.setRating(i);
      restaurant.setAddress("TestAddress : " + i);
      restaurant.setName("TestName : " + i);
      restaurant.setReviewCount(i);
      restaurants.add(restaurant);
    }

    return restaurants;
  }
}

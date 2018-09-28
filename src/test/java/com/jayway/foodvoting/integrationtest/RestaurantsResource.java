package com.jayway.foodvoting.integrationtest;

import com.jayway.foodvoting.model.Restaurant;
import java.util.ArrayList;
import java.util.List;

public class RestaurantsResource {

  public static List<Restaurant> getManyRestaurantsResource() {

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

  public static List<Restaurant> getOneRestaurantResource() {

    List<Restaurant> restaurants = new ArrayList<>();

    Restaurant restaurant = new Restaurant();
    restaurant.setRating(3);
    restaurant.setAddress("TestAddress");
    restaurant.setName("TestName");
    restaurant.setReviewCount(5);
    restaurants.add(restaurant);

    return restaurants;
  }
}

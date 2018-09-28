package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.Restaurant;
import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.utility.RestaurantFilter;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

  private YelpRestaurantFetcher yelpRestaurantFetcher;
  private Restaurant suggestedRestaurant;

  public RestaurantService(YelpRestaurantFetcher yelpRestaurantFetcher) {
    this.yelpRestaurantFetcher = yelpRestaurantFetcher;
  }

  public Restaurant getBusinessOfTheDay() {
    LOGGER.info("GET BUSINESS OF THE DAY");

    List<Restaurant> unfilteredRestaurants = yelpRestaurantFetcher.getYelpRestaurants();
    if (unfilteredRestaurants == null) {
      LOGGER.warn("NO RESTAURANTS FOUND");
      return null;
    }

    List<Restaurant> filteredRestaurants = RestaurantFilter
        .restaurantGradeFilter(unfilteredRestaurants);

    if (filteredRestaurants == null) {
      LOGGER.warn("NO RESTAURANTS LEFT AFTER WEED OUT");
      return null;
    }

    int randomInt = new Random().nextInt((filteredRestaurants.size()));

    if (getSuggestedRestaurant() != null && getSuggestedRestaurant()
        .equals(filteredRestaurants.get(randomInt))) {
      randomInt++;
      if (randomInt >= filteredRestaurants.size()) {
        randomInt = 0;
      }
      setSuggestedRestaurant(filteredRestaurants.get(randomInt));
    } else {
      setSuggestedRestaurant(filteredRestaurants.get(randomInt));
    }
    return getSuggestedRestaurant();
  }

  private Restaurant getSuggestedRestaurant() {
    synchronized (this) {
      return suggestedRestaurant;
    }
  }

  private void setSuggestedRestaurant(Restaurant suggestedRestaurant) {
    synchronized (this) {
      this.suggestedRestaurant = suggestedRestaurant;
    }
  }
}


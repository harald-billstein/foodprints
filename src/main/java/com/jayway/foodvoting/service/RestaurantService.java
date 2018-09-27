package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
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
  private Business suggestedRestaurant;

  public RestaurantService(YelpRestaurantFetcher yelpRestaurantFetcher) {
    this.yelpRestaurantFetcher = yelpRestaurantFetcher;
  }

  public ResponseEntity<RestaurantSuggestionResponse> getBusinessOfTheDay() {
    LOGGER.info("GET BUSINESS OF THE DAY");

    Restaurants unfilteredRestaurants = yelpRestaurantFetcher.getRestaurants();
    if (unfilteredRestaurants == null) {
      LOGGER.warn("NO RESTAURANTS FOUND");
      return getResponse(null);
    }

    List<Business> filteredRestaurants = RestaurantFilter
        .RestaurantGradeFilter(unfilteredRestaurants);

    if (filteredRestaurants == null) {
      LOGGER.warn("NO RESTAURANTS LEFT AFTER WEED OUT");
      return getResponse(null);
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
    return getResponse(getSuggestedRestaurant());
  }

  private ResponseEntity<RestaurantSuggestionResponse> getResponse(Business suggestedRestaurant) {

    if (suggestedRestaurant != null) {
      RestaurantSuggestionResponse restaurantSuggestionResponse = new RestaurantSuggestionResponse();
      restaurantSuggestionResponse.setAddress(suggestedRestaurant.getLocation().getAddress1());
      restaurantSuggestionResponse.setGrade(suggestedRestaurant.getRating());
      restaurantSuggestionResponse.setName(suggestedRestaurant.getName());
      return ResponseEntity.ok(restaurantSuggestionResponse);
    } else {
      return ResponseEntity.noContent().build();
    }
  }

  private Business getSuggestedRestaurant() {
    synchronized (this) {
      return suggestedRestaurant;
    }
  }

  private void setSuggestedRestaurant(Business suggestedRestaurant) {
    synchronized (this) {
      this.suggestedRestaurant = suggestedRestaurant;
    }
  }
}


package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.model.yelp.Business;
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

    List<Business> restaurants = RestaurantFilter
        .RestaurantGradeFilter(yelpRestaurantFetcher.getRestaurants());

    int randomInt = new Random().nextInt((restaurants.size()));

    if (getSuggestedRestaurant() != null && getSuggestedRestaurant()
        .equals(restaurants.get(randomInt))) {
      randomInt++;
      if (randomInt >= restaurants.size()) {
        randomInt = 0;
      }
      setSuggestedRestaurant(restaurants.get(randomInt));
    } else {
      setSuggestedRestaurant(restaurants.get(randomInt));
    }

    return getResponse(getSuggestedRestaurant());
  }

  private ResponseEntity<RestaurantSuggestionResponse> getResponse(Business suggestedRestaurant) {

    RestaurantSuggestionResponse restaurantSuggestionResponse = new RestaurantSuggestionResponse();
    restaurantSuggestionResponse.setAddress(suggestedRestaurant.getLocation().getAddress1());
    restaurantSuggestionResponse.setGrade(suggestedRestaurant.getRating());
    restaurantSuggestionResponse.setName(suggestedRestaurant.getName());

    return ResponseEntity.ok(restaurantSuggestionResponse);
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


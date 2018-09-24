package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.utility.RestaurantFilter;
import java.util.List;
import java.util.Random;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  private YelpRestaurantFetcher yelpRestaurantFetcher;
  private Business suggestedRestaurant;
  private String lastSuggestedRestaurant;

  public RestaurantService(YelpRestaurantFetcher yelpRestaurantFetcher) {
    this.yelpRestaurantFetcher = yelpRestaurantFetcher;
  }

  public ResponseEntity<RestaurantSuggestionResponse> getBusinessOfTheDay() {

    List<Business> restaurants = RestaurantFilter
        .RestaurantGradeFilter(yelpRestaurantFetcher.getRestaurants());

    int randomInt = new Random().nextInt((restaurants.size()));

    if (suggestedRestaurant == null) {
      suggestedRestaurant = restaurants.get(randomInt);
      lastSuggestedRestaurant = restaurants.get(randomInt).getName();
    } else {

      if (lastSuggestedRestaurant.equalsIgnoreCase(restaurants.get(randomInt).getName())) {
        randomInt++;

        if (randomInt >= restaurants.size()) {
          randomInt = 0;
        }

        suggestedRestaurant = restaurants.get(randomInt);
        lastSuggestedRestaurant = restaurants.get(randomInt).getName();
      } else {
        suggestedRestaurant = restaurants.get(randomInt);
        lastSuggestedRestaurant = restaurants.get(randomInt).getName();
      }
      suggestedRestaurant = restaurants.get(randomInt);
      lastSuggestedRestaurant = restaurants.get(randomInt).getName();
    }
    return getResponse(suggestedRestaurant);
  }

  private ResponseEntity<RestaurantSuggestionResponse> getResponse(Business suggestedRestaurant) {

    RestaurantSuggestionResponse restaurantSuggestionResponse = new RestaurantSuggestionResponse();
    restaurantSuggestionResponse.setAddress(suggestedRestaurant.getLocation().getAddress1());
    restaurantSuggestionResponse.setGrade(suggestedRestaurant.getRating());
    restaurantSuggestionResponse.setName(suggestedRestaurant.getName());

    return ResponseEntity.ok(restaurantSuggestionResponse);
  }
}


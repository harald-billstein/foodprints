package com.jayway.foodvoting;

import com.jayway.foodvoting.service.Restaurants;
import com.jayway.foodvoting.service.RestaurantsSuggestion;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class FoodvotingApplication {

  public static void main(String[] args) {
    SpringApplication.run(FoodvotingApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    RestaurantsSuggestion restaurantsSuggestion = new RestaurantsSuggestion();
    Restaurants restaurants = restaurantsSuggestion.getRestaurants();

    System.out.println(restaurants.getRegion().getCenter().getLatitude());


  }
}

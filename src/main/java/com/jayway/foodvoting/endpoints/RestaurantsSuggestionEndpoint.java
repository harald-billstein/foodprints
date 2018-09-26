package com.jayway.foodvoting.endpoints;

import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1")
public class RestaurantsSuggestionEndpoint {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
  private RestaurantService restaurantService;

  public RestaurantsSuggestionEndpoint(RestaurantService restaurantService) {
    this.restaurantService = restaurantService;
  }

  @GetMapping(value = "/restaurants/suggestion")
  public ResponseEntity<RestaurantSuggestionResponse> getRestaurant() {
    LOGGER.info("ENDPOINT : SUGGESTION");
    return restaurantService.getBusinessOfTheDay();
  }
}

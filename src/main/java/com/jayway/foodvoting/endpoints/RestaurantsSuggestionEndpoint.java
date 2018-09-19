package com.jayway.foodvoting.endpoints;

import com.jayway.foodvoting.model.VoteResponse;
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

  @GetMapping(value = "/restaurants/suggestions")
  public ResponseEntity<VoteResponse> getRestaurants() {
    LOGGER.info("ENDPOINT TRIGGERED : /restaurants/suggestions");
    return null;
  }

}

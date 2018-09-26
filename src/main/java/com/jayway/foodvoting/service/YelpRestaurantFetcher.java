package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.yelp.Restaurants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class YelpRestaurantFetcher {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private String baseURL = "https://api.yelp.com/v3";
  private String resource = "/businesses";
  private String action = "/search";
  private String location = "Klara Östra Kyrkogata";
  private String term = "vegetarian";
  private String radius = "500";
  private String categories = "restaurants";
  private String token = "Bearer wXDO-HlZMD8J7OO0uS6H9E_oAqd5QC2b7JxZ3ms1eEj3RFcHEN8CcVqHKSnpSymT2VZm80Pppb5pXQOZodbTiW1W9lN-tKYrDIbDEjWKYYsHSUvy4a2ip-kc5fKgW3Yx";
  private Flux<Restaurants> restaurants;
  private Object key = new Object();

  public YelpRestaurantFetcher() {
    updateBusinessList();
  }

  // EVERY DAY AT MIDNIGHT
  @Scheduled(cron = "0 0 0 * * *")
  private void updateBusinessList() {
    LOGGER.info("UPDATING BUSINESS LIST");

    WebClient client = WebClient.create(baseURL);

    Flux<Restaurants> restaurants = client.get()
        .uri(uriBuilder -> uriBuilder.path(resource + action)
        .queryParam("location", location)
        .queryParam("term", term)
        .queryParam("radius", radius)
        .queryParam("categories", categories)
        .build())
        .header("Authorization", token)
        .retrieve()
        .onStatus(HttpStatus::isError, clientResponse -> Mono.error(new Throwable("API DOWN")))
        .bodyToFlux(Restaurants.class);

    setRestaurants(restaurants);
  }

  public Flux<Restaurants> getRestaurants() {
    synchronized (key) {
      return restaurants;
    }
  }

  private void setRestaurants(Flux<Restaurants> restaurants) {
    synchronized (key) {
      this.restaurants = restaurants;
    }
  }
}

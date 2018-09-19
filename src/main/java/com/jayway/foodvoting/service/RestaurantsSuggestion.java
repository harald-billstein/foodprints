package com.jayway.foodvoting.service;

import com.google.gson.Gson;
import com.jayway.foodvoting.utility.ParameterStringBuilder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestaurantsSuggestion {

  private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

  private String baseURL = "https://api.yelp.com/v3";
  private String resource = "/businesses";
  private String action = "/search";
  private String location = "Klara Östra Kyrkogata";
  private String term = "vegan";
  private String radius = "500";
  private String categories = "restaurants";
  private String token = "Bearer wXDO-HlZMD8J7OO0uS6H9E_oAqd5QC2b7JxZ3ms1eEj3RFcHEN8CcVqHKSnpSymT2VZm80Pppb5pXQOZodbTiW1W9lN-tKYrDIbDEjWKYYsHSUvy4a2ip-kc5fKgW3Yx";
  private Restaurants restaurants;

  public RestaurantsSuggestion() {
    updateBusinessList();
  }

  private void updateBusinessList() {
    LOGGER.info("UPDATING BUSINESS LIST");
    try {
      URL url = new URL(buildUrl());
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);
      connection.setRequestProperty("Authorization", token);

      if (connection.getResponseCode() == 200) {
        restaurants = retrieveBusinessesObject(retrieveMessage(connection));
      } else {
        throw new Exception("Connection status " + connection.getResponseCode());
      }
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  private Restaurants retrieveBusinessesObject(String message) {
    Gson gson = new Gson();
    return gson.fromJson(message, Restaurants.class);
  }

  private String retrieveMessage(HttpURLConnection con) throws Exception {
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder content = new StringBuilder();
    while ((inputLine = in.readLine()) != null) {
      content.append(inputLine);
    }
    in.close();

    return content.toString();
  }

  private String buildUrl() {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("location", location);
    parameters.put("term", term);
    parameters.put("radius", radius);
    parameters.put("categories", categories);

    return baseURL + resource + action + ParameterStringBuilder.getParameterString(parameters);

  }

  public Restaurants getRestaurants() {
    return restaurants;
  }
}

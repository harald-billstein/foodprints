package com.jayway.foodvoting.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.foodvoting.model.Restaurant;
import com.jayway.foodvoting.model.RestaurantSuggestionResponse;
import com.jayway.foodvoting.service.YelpRestaurantFetcher;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
public class RestaurantSuggestionsEndpointTest extends IntegrationTest {

  private static final int MIN_RATING = 3;
  private static final int MIN_NAME = 2;
  private static final int MIN_ADDRESS_LENGTH = 5;
  private static final long NO_CONTENT_RESPONSE = 204;
  private static final long BAD_PATH_RESPONSE = 404;

  private static final String PATH = "https://localhost:8443/v1/restaurants/suggestion";
  private static final String BAD_PATH = "https://localhost:8443/bad/path";

  @MockBean
  private YelpRestaurantFetcher yelpRestaurantFetcher;

  public void setUpMock(boolean brokenLink) {

    if (!brokenLink) {
      List<Restaurant> restaurants = RestaurantsResource.getRestaurantResource();
      Mockito.when(yelpRestaurantFetcher.getYelpRestaurants())
          .thenReturn(restaurants);
    } else {
      Mockito.when(yelpRestaurantFetcher.getYelpRestaurants())
          .thenReturn(null);
    }
  }

  @Test
  public void fetchRestaurantsTest() throws Exception {
    setUpMock(false);
    ObjectMapper objectMapper = new ObjectMapper();

    RestaurantSuggestionResponse firstResultObject;
    RestaurantSuggestionResponse secondResultObject;

    String firstJsonResponse;
    String secondJsonResponse;

    ResultActions firstResult;
    ResultActions secondResult;

    firstResult = this.mvcPerformValidGet(PATH);
    secondResult = this.mvcPerformValidGet(PATH);

    firstJsonResponse = firstResult.andReturn().getResponse().getContentAsString();
    secondJsonResponse = secondResult.andReturn().getResponse().getContentAsString();

    firstResultObject = objectMapper
        .readValue(firstJsonResponse, RestaurantSuggestionResponse.class);
    secondResultObject = objectMapper
        .readValue(secondJsonResponse, RestaurantSuggestionResponse.class);

    Assert.assertTrue(firstResultObject.getAddress().length() > MIN_ADDRESS_LENGTH);
    Assert.assertTrue(firstResultObject.getName().length() > MIN_NAME);
    Assert.assertTrue(firstResultObject.getGrade() > MIN_RATING);

    Assert.assertNotEquals(firstResultObject.getName(), secondResultObject.getName());
  }

  @Test
  public void fetchRestaurantsNoSuggestionsTest() throws Exception {
    setUpMock(true);

    ResultActions result = this.mvcPerformValidGet(PATH);
    long response = result.andReturn().getResponse().getStatus();
    Assert.assertEquals(NO_CONTENT_RESPONSE, response);
  }

  @Test
  public void fetchRestaurantsBadUrlTest() throws Exception {
    ResultActions result = this.mvcPerformValidGet(BAD_PATH);
    long response = result.andReturn().getResponse().getStatus();
    Assert.assertEquals(BAD_PATH_RESPONSE, response);
  }
}

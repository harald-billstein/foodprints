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
import org.springframework.http.HttpStatus;
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

  private static final String PATH = "https://localhost:8443/v1/restaurants/suggestion";

  @MockBean
  private YelpRestaurantFetcher yelpRestaurantFetcher;

  public void setUpMock(boolean brokenLink, List<Restaurant> restaurants) {

    if (brokenLink) {
      Mockito.when(yelpRestaurantFetcher.getYelpRestaurants()).thenReturn(null);
    } else {
      Mockito.when(yelpRestaurantFetcher.getYelpRestaurants()).thenReturn(restaurants);
    }
  }

  @Test
  public void fetchRestaurantsTest() throws Exception {
    setUpMock(false, RestaurantsResource.getManyRestaurantsResource());
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
    setUpMock(true, null);

    ResultActions result = this.mvcPerformValidGet(PATH);
    HttpStatus response = HttpStatus.valueOf(result.andReturn().getResponse().getStatus());
    Assert.assertEquals(HttpStatus.NO_CONTENT, response);
  }

  @Test
  public void fetchRestaurantsEmptyListReceivedSuggestionsTest() throws Exception {
    setUpMock(false, RestaurantsResource.getOneRestaurantResource());
    ObjectMapper objectMapper = new ObjectMapper();

    ResultActions result = this.mvcPerformValidGet(PATH);
    String jsonResponse = result.andReturn().getResponse().getContentAsString();
    RestaurantSuggestionResponse resultObject = objectMapper
        .readValue(jsonResponse, RestaurantSuggestionResponse.class);

    Assert.assertTrue(resultObject.getName().length() >= MIN_NAME);
    Assert.assertTrue(resultObject.getGrade() >= MIN_RATING);
    Assert.assertTrue(resultObject.getAddress().length() >= MIN_ADDRESS_LENGTH);
  }
}

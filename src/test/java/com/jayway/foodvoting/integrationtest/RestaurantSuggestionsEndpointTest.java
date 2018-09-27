package com.jayway.foodvoting.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.foodvoting.model.yelp.Business;
import com.jayway.foodvoting.model.yelp.Restaurants;
import com.jayway.foodvoting.service.YelpRestaurantFetcher;
import com.jayway.foodvoting.utility.RestaurantFilter;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
public class RestaurantSuggestionsEndpointTest {

  private static final int MIN_RATING = 3;
  private static final int MIN_VOTES = 5;
  private static final int MIN_ADDRESS_LENGTH = 5;

  @MockBean
  private YelpRestaurantFetcher yelpRestaurantFetcher;
  private String jsonBusinesses = RestaurantsJsonWrapper.getRestaurantSuggestionsEndpointJSON();

  @Before
  public void setUpMock() {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      Mockito.when(yelpRestaurantFetcher.getRestaurants())
          .thenReturn(objectMapper.readValue(jsonBusinesses, Restaurants.class));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void fetchRestaurantsTest() {
    Assert.assertNotNull(yelpRestaurantFetcher.getRestaurants());
  }

  @Test
  public void filterWeedingCheck() {
    Restaurants restaurants = yelpRestaurantFetcher.getRestaurants();
    List<Business> unFiltered = restaurants.getBusinesses();
    List<Business> filtered = RestaurantFilter.RestaurantGradeFilter(restaurants);
    Assert.assertTrue(unFiltered.size() > filtered.size());
  }

  @Test
  public void filterResultChecker() {
    Restaurants restaurants = yelpRestaurantFetcher.getRestaurants();
    List<Business> filtered = RestaurantFilter.RestaurantGradeFilter(restaurants);

    for (Business business : filtered) {
      Assert.assertTrue("Rating test failed at : " + business.getName(),
          business.getRating() > MIN_RATING);
      Assert.assertTrue("Vote count failed at : " + business.getName(),
          business.getReview_count() > MIN_VOTES);
      Assert.assertTrue("Address not present at : " + business.getName(),
          business.getLocation().getAddress1().length() > MIN_ADDRESS_LENGTH);
    }
  }
}

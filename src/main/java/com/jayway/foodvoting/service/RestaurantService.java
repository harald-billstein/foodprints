package com.jayway.foodvoting.service;

import com.jayway.foodvoting.enums.FoodPicks;
import com.jayway.foodvoting.dao.CollectionOfVotes;
import com.jayway.foodvoting.model.Restaurant;
import com.jayway.foodvoting.model.VoteResponse;
import com.jayway.foodvoting.repository.FoodTypeRepository;
import com.jayway.foodvoting.repository.VotingRepositoryIMP;
import com.jayway.foodvoting.utility.RestaurantFilter;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);

  private YelpRestaurantFetcher yelpRestaurantFetcher;
  private Restaurant suggestedRestaurant;

  public RestaurantService(YelpRestaurantFetcher yelpRestaurantFetcher) {
    this.yelpRestaurantFetcher = yelpRestaurantFetcher;
  }

  public Restaurant getBusinessOfTheDay() {
    LOGGER.info("GET BUSINESS OF THE DAY");

    List<Restaurant> unfilteredRestaurants = yelpRestaurantFetcher.getYelpRestaurants();
    if (unfilteredRestaurants == null) {
      LOGGER.warn("NO RESTAURANTS FOUND");
      return null;
    }

    List<Restaurant> filteredRestaurants = RestaurantFilter
        .restaurantGradeFilter(unfilteredRestaurants);

    if (filteredRestaurants.size() <= 0) {
      LOGGER.warn("NO RESTAURANTS LEFT AFTER WEED OUT");
      return null;
    }

    int randomInt = new Random().nextInt((filteredRestaurants.size()));

    if (getSuggestedRestaurant() != null && getSuggestedRestaurant()
        .equals(filteredRestaurants.get(randomInt))) {
      randomInt++;
      if (randomInt >= filteredRestaurants.size()) {
        randomInt = 0;
      }
      setSuggestedRestaurant(filteredRestaurants.get(randomInt));
    } else {
      setSuggestedRestaurant(filteredRestaurants.get(randomInt));
    }
    return getSuggestedRestaurant();
  }

  private Restaurant getSuggestedRestaurant() {
    synchronized (this) {
      return suggestedRestaurant;
    }
  }

  private void setSuggestedRestaurant(Restaurant suggestedRestaurant) {
    synchronized (this) {
      this.suggestedRestaurant = suggestedRestaurant;
    }
  }

  @Component
  public static class VoteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteService.class);
    private VotingRepositoryIMP votingRepositoryIMP;
    //private VotingRepository votingRepository;
    private FoodTypeRepository foodTypeRepository;

    public VoteService(VotingRepositoryIMP votingRepositoryIMP,
        FoodTypeRepository foodTypeRepository) {
      this.votingRepositoryIMP = votingRepositoryIMP;
      //this.votingRepository = votingRepository;
      this.foodTypeRepository = foodTypeRepository;
    }

    public ResponseEntity<VoteResponse> registerVote(String vote) {
      VoteResponse voteResponse = new VoteResponse();

      if (inputCheck(vote)) {
        CreateVotePickIfNeeded(vote);

        CollectionOfVotes collection = votingRepositoryIMP
            .getTodyasVoteCategory(FoodPicks.valueOf(vote));

        int votes = collection.getVotes() + 1;
        collection.setVotes(votes);
        votingRepositoryIMP.saveCollectionOfVotes(collection);

        voteResponse.setSuccess(true);
        voteResponse.setMessage("VOTE REGISTERED");

        return ResponseEntity.ok(voteResponse);
      } else {
        voteResponse.setSuccess(false);
        voteResponse.setMessage("INVALID INPUT");

        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(voteResponse);
      }
    }

    private boolean inputCheck(String input) {
      LOGGER.info("INPUTCHECKER");
      boolean match = false;

      var foodPicks = FoodPicks.values();
      for (FoodPicks foodPick : foodPicks) {
        if (foodPick.name().equalsIgnoreCase(input)) {
          LOGGER.info("MATCH FOUND!");
          match = true;
        }
      }
      return match;
    }

    private void CreateVotePickIfNeeded(String input) {
      List<CollectionOfVotes> categorys = votingRepositoryIMP.getTodaysVoteCategorys();
      boolean foundEntry = false;

      for (CollectionOfVotes category : categorys) {
        LOGGER.info(category.getCategory().getCategory().toString());
        if (category.getCategory().getCategory().name().equalsIgnoreCase(input)) {
          LOGGER.info("CREATE NOT NEEDED CATEGORY FOUND");
          foundEntry = true;
        }
      }

      if (!foundEntry) {
        LOGGER.info("CREATING CATEGORY NO PREVIOUS FOUND");
        CollectionOfVotes collectionOfVotes = new CollectionOfVotes();
        collectionOfVotes.setLocalDate(LocalDate.now());
        collectionOfVotes.setVotes(0);
        collectionOfVotes.setCategory(foodTypeRepository.findByCategory(input));
        votingRepositoryIMP.saveCollectionOfVotes(collectionOfVotes);
      }
    }
  }
}


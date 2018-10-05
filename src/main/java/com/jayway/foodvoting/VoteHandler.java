package com.jayway.foodvoting;

import com.jayway.foodvoting.dao.Emission;
import com.jayway.foodvoting.enums.FoodPicks;
import com.jayway.foodvoting.model.CollectionOfVotes;
import com.jayway.foodvoting.model.VoteResponse;
import com.jayway.foodvoting.repository.FoodTypeRepository;
import com.jayway.foodvoting.repository.VoteingRepository;
import com.jayway.foodvoting.repository.VotingRepositoryIMP;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class VoteHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(VoteHandler.class);
  private VotingRepositoryIMP votingRepositoryIMP;
  //private VoteingRepository voteingRepository;
  private FoodTypeRepository foodTypeRepository;

  public VoteHandler(VotingRepositoryIMP votingRepositoryIMP, FoodTypeRepository foodTypeRepository) {
    this.votingRepositoryIMP = votingRepositoryIMP;
    //this.voteingRepository = voteingRepository;
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

    if (categorys.size() <= 5) {
      for (CollectionOfVotes category : categorys) {
        LOGGER.info(category.getCategory().getCategory().toString());
        if (category.getCategory().getCategory().name().equalsIgnoreCase(input)) {
          LOGGER.info("CREATE NOT NEEDED CATEGORY FOUND");
          foundEntry = true;
        }
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

package com.jayway.foodvoting.repository;

import com.jayway.foodvoting.enums.FoodPicks;
import com.jayway.foodvoting.dao.CollectionOfVotes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VotingRepositoryIMP {

  private static final Logger LOGGER = LoggerFactory.getLogger(VotingRepositoryIMP.class);

  private VoteingRepository voteingRepository;
  private FoodTypeRepository foodTypeRepository;

  public VotingRepositoryIMP(VoteingRepository voteingRepository,
      FoodTypeRepository foodTypeRepository) {
    this.voteingRepository = voteingRepository;
    this.foodTypeRepository = foodTypeRepository;
  }

  public CollectionOfVotes saveCollectionOfVotes(CollectionOfVotes collectionOfVotes) {
    LOGGER.info("SAVING:::" + collectionOfVotes.getCategory());
    return voteingRepository.save(collectionOfVotes);
  }

  public List<CollectionOfVotes> getTodaysVoteCategorys() {
    var allVotes = voteingRepository.findAll().iterator();
    List<CollectionOfVotes> VoteCategoriesForToday = new ArrayList<>();

    while (allVotes.hasNext()) {
      var cat = allVotes.next();
      if (cat.getLocalDate().isEqual(LocalDate.now())) {
        VoteCategoriesForToday.add(cat);
      }
    }
    return VoteCategoriesForToday;
  }

  public CollectionOfVotes getTodyasVoteCategory(FoodPicks foodPick) {
    var allVotes = voteingRepository.findAll().iterator();
    CollectionOfVotes cat = null;

    while (allVotes.hasNext()) {
      cat = allVotes.next();
      if (cat.getLocalDate().isEqual(LocalDate.now()) && cat.getCategory().getCategory().name()
          .equalsIgnoreCase(foodPick.name())) {
        return cat;
      }
    }

    if (cat != null) {
      return cat;
    } else {
      throw new RuntimeException("Category in Database is null");
    }
  }
}

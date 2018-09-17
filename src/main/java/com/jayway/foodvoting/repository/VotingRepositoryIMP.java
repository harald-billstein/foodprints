package com.jayway.foodvoting.repository;

import com.jayway.foodvoting.model.CollectionOfVotes;
import org.springframework.stereotype.Component;

@Component
public class VotingRepositoryIMP {

  private VoteingRepository voteingRepository;

  public VotingRepositoryIMP(VoteingRepository voteingRepository) {
    this.voteingRepository = voteingRepository;
  }

  public CollectionOfVotes saveCollectionOfVotes(CollectionOfVotes collectionOfVotes) {
    return voteingRepository.save(collectionOfVotes);
  }
}

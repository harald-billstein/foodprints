package com.jayway.foodvoting.repository;

import com.jayway.foodvoting.model.CollectionOfVotes;
import org.springframework.data.repository.CrudRepository;

public interface VoteingRepository extends CrudRepository<CollectionOfVotes, Integer> {
}

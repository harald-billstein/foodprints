package com.jayway.foodvoting.bootstrap;

import com.jayway.foodvoting.dao.CollectionOfVotes;
import com.jayway.foodvoting.dao.Emission;
import com.jayway.foodvoting.repository.FoodTypeRepository;
import com.jayway.foodvoting.repository.VoteingRepository;
import com.jayway.foodvoting.repository.VotingRepositoryIMP;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private VotingRepositoryIMP votingRepositoryIMP;
  private VoteingRepository voteingRepository;
  private FoodTypeRepository foodTypeRepository;

  public DevBootstrap(VotingRepositoryIMP votingRepositoryIMP, VoteingRepository voteingRepository,
      FoodTypeRepository foodTypeRepository) {
    this.votingRepositoryIMP = votingRepositoryIMP;
    this.voteingRepository = voteingRepository;
    this.foodTypeRepository = foodTypeRepository;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    init();
  }

  private void init() {
    initBeef();

    Map<String, Emission> foodTypes = new HashMap<>();
    for (Emission e : foodTypeRepository.findAll()) {
      foodTypes.put(e.getCategory().name(), e);
    }

    var history = 5;

    CollectionOfVotes collectionOfMeatVotes;
    CollectionOfVotes collectionOfChickenVotes;
    CollectionOfVotes collectionOfFishVotes;
    CollectionOfVotes collectionOfVegetarianVotes;
    CollectionOfVotes collectionOfVeganVotes;
    CollectionOfVotes collectionOfPorkVotes;

    collectionOfFishVotes = new CollectionOfVotes();
    collectionOfFishVotes.setLocalDate(LocalDate.of(2018, 1, 1));
    collectionOfFishVotes.setCategory(foodTypes.get("FISH"));
    collectionOfFishVotes.setVotes(11);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfFishVotes);

    collectionOfMeatVotes = new CollectionOfVotes();
    collectionOfMeatVotes.setLocalDate(LocalDate.of(2018, 2, 1));
    collectionOfMeatVotes.setCategory(foodTypes.get("BEEF"));
    collectionOfMeatVotes.setVotes(12);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfMeatVotes);

    collectionOfVeganVotes = new CollectionOfVotes();
    collectionOfVeganVotes.setLocalDate(LocalDate.of(2018, 3, 1));
    collectionOfVeganVotes.setCategory(foodTypes.get("VEGAN"));
    collectionOfVeganVotes.setVotes(13);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfVeganVotes);

    collectionOfMeatVotes = new CollectionOfVotes();
    collectionOfMeatVotes.setLocalDate(LocalDate.of(2018,4,1));
    collectionOfMeatVotes.setCategory(foodTypes.get("BEEF"));
    // RANDOM INT 1-10
    collectionOfMeatVotes.setVotes(14);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfMeatVotes);

    collectionOfChickenVotes = new CollectionOfVotes();
    collectionOfChickenVotes.setLocalDate(LocalDate.of(2018,5,1));
    collectionOfChickenVotes.setCategory(foodTypes.get("CHICKEN"));
    collectionOfChickenVotes.setVotes(15);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfChickenVotes);

    collectionOfFishVotes = new CollectionOfVotes();
    collectionOfFishVotes.setLocalDate(LocalDate.of(2018,6,1));
    collectionOfFishVotes.setCategory(foodTypes.get("FISH"));
    collectionOfFishVotes.setVotes(16);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfFishVotes);

    collectionOfVegetarianVotes = new CollectionOfVotes();
    collectionOfVegetarianVotes.setLocalDate(LocalDate.of(2018,7,1));
    collectionOfVegetarianVotes.setCategory(foodTypes.get("VEGETARIAN"));
    collectionOfVegetarianVotes.setVotes(17);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfVegetarianVotes);

    collectionOfVeganVotes = new CollectionOfVotes();
    collectionOfVeganVotes.setLocalDate(LocalDate.of(2018,8,1));
    collectionOfVeganVotes.setCategory(foodTypes.get("VEGAN"));
    collectionOfVeganVotes.setVotes(18);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfVeganVotes);

    collectionOfPorkVotes = new CollectionOfVotes();
    collectionOfPorkVotes.setLocalDate(LocalDate.of(2018,10,1));
    collectionOfPorkVotes.setCategory(foodTypes.get("PORK"));
    collectionOfPorkVotes.setVotes(19);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfPorkVotes);

    collectionOfPorkVotes = new CollectionOfVotes();
    collectionOfPorkVotes.setLocalDate(LocalDate.of(2018,11,1));
    collectionOfPorkVotes.setCategory(foodTypes.get("PORK"));
    collectionOfPorkVotes.setVotes(11);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfPorkVotes);

    collectionOfPorkVotes = new CollectionOfVotes();
    collectionOfPorkVotes.setLocalDate(LocalDate.of(2018,12,1));
    collectionOfPorkVotes.setCategory(foodTypes.get("PORK"));
    collectionOfPorkVotes.setVotes(12);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfPorkVotes);


  }

  // FOR TESTS DONT REMOVE
  private void initBeef() {
    Emission beef = foodTypeRepository.findByCategory("BEEF");

    voteingRepository.saveAndFlush(new CollectionOfVotes(LocalDate.parse("2018-09-10"), beef, 4));
    voteingRepository.saveAndFlush(new CollectionOfVotes(LocalDate.parse("2018-09-11"), beef, 1));
    voteingRepository.saveAndFlush(new CollectionOfVotes(LocalDate.parse("2018-09-12"), beef, 1));
    voteingRepository.saveAndFlush(new CollectionOfVotes(LocalDate.parse("2018-09-13"), beef, 3));
    voteingRepository.saveAndFlush(new CollectionOfVotes(LocalDate.parse("2018-09-14"), beef, 1));
  }
}

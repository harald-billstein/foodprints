package com.jayway.foodvoting.bootstrap;

import com.jayway.foodvoting.dao.Emission;
import com.jayway.foodvoting.dao.CollectionOfVotes;
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
    collectionOfFishVotes.setLocalDate(LocalDate.now().minusMonths(8));
    collectionOfFishVotes.setCategory(foodTypes.get("FISH"));
    collectionOfFishVotes.setVotes(1);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfFishVotes);

    collectionOfMeatVotes = new CollectionOfVotes();
    collectionOfMeatVotes.setLocalDate(LocalDate.now().minusMonths(8));
    collectionOfMeatVotes.setCategory(foodTypes.get("BEEF"));
    collectionOfMeatVotes.setVotes(1);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfMeatVotes);

    collectionOfVeganVotes = new CollectionOfVotes();
    collectionOfVeganVotes.setLocalDate(LocalDate.now().minusMonths(9));
    collectionOfVeganVotes.setCategory(foodTypes.get("VEGAN"));
    collectionOfVeganVotes.setVotes(1);
    votingRepositoryIMP.saveCollectionOfVotes(collectionOfVeganVotes);

    // new Random().nextInt(((10 - 2) + 1) + 2)

    for (int i = 0; i < history; i++) {
      collectionOfMeatVotes = new CollectionOfVotes();
      collectionOfMeatVotes.setLocalDate(LocalDate.now().minusMonths(i + 1));
      collectionOfMeatVotes.setCategory(foodTypes.get("BEEF"));
      // RANDOM INT 1-10
      collectionOfMeatVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfMeatVotes);

      collectionOfChickenVotes = new CollectionOfVotes();
      collectionOfChickenVotes.setLocalDate(LocalDate.now().minusMonths(i + 1));
      collectionOfChickenVotes.setCategory(foodTypes.get("CHICKEN"));
      collectionOfChickenVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfChickenVotes);

      collectionOfFishVotes = new CollectionOfVotes();
      collectionOfFishVotes.setLocalDate(LocalDate.now().minusMonths(i + 1));
      collectionOfFishVotes.setCategory(foodTypes.get("FISH"));
      collectionOfFishVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfFishVotes);

      collectionOfVegetarianVotes = new CollectionOfVotes();
      collectionOfVegetarianVotes.setLocalDate(LocalDate.now().minusMonths(i + 2));
      collectionOfVegetarianVotes.setCategory(foodTypes.get("VEGETARIAN"));
      collectionOfVegetarianVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfVegetarianVotes);

      collectionOfVeganVotes = new CollectionOfVotes();
      collectionOfVeganVotes.setLocalDate(LocalDate.now().minusMonths(i + 1));
      collectionOfVeganVotes.setCategory(foodTypes.get("VEGAN"));
      collectionOfVeganVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfVeganVotes);

      collectionOfPorkVotes = new CollectionOfVotes();
      collectionOfPorkVotes.setLocalDate(LocalDate.now().minusMonths(i + 1));
      collectionOfPorkVotes.setCategory(foodTypes.get("PORK"));
      collectionOfPorkVotes.setVotes(1);
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfPorkVotes);
    }
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

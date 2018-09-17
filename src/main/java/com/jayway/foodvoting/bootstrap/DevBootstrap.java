package com.jayway.foodvoting.bootstrap;

import com.jayway.foodvoting.enums.FoodPicks;
import com.jayway.foodvoting.model.CollectionOfVotes;
import com.jayway.foodvoting.repository.VotingRepositoryIMP;
import java.time.LocalDate;
import java.util.Random;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "dev")
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private VotingRepositoryIMP votingRepositoryIMP;

  public DevBootstrap(VotingRepositoryIMP votingRepositoryIMP) {
    this.votingRepositoryIMP = votingRepositoryIMP;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    init();
  }

  private void init() {

    int history = 5;

    CollectionOfVotes collectionOfMeatVotes;
    CollectionOfVotes collectionOfChickenVotes;
    CollectionOfVotes collectionOfFishVotes;
    CollectionOfVotes collectionOfVegetarianVotes;
    CollectionOfVotes collectionOfVeganVotes;

    for (int i = 0; i < history; i++) {
      collectionOfMeatVotes = new CollectionOfVotes();
      collectionOfMeatVotes.setLocalDate(LocalDate.now().minusDays(i));
      collectionOfMeatVotes.setFoodPick(FoodPicks.MEAT);
      // RANDOM INT 1-10
      collectionOfMeatVotes.setVotes(new Random().nextInt(((10 - 2) + 1) + 2));
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfMeatVotes);

      collectionOfChickenVotes = new CollectionOfVotes();
      collectionOfChickenVotes.setLocalDate(LocalDate.now().minusDays(i));
      collectionOfChickenVotes.setFoodPick(FoodPicks.CHICKEN);
      collectionOfChickenVotes.setVotes(new Random().nextInt(((10 - 2) + 1) + 2));
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfChickenVotes);

      collectionOfFishVotes = new CollectionOfVotes();
      collectionOfFishVotes.setLocalDate(LocalDate.now().minusDays(i));
      collectionOfFishVotes.setFoodPick(FoodPicks.FISH);
      collectionOfFishVotes.setVotes(new Random().nextInt(((10 - 2) + 1) + 2));
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfFishVotes);

      collectionOfVegetarianVotes = new CollectionOfVotes();
      collectionOfVegetarianVotes.setLocalDate(LocalDate.now().minusDays(i));
      collectionOfVegetarianVotes.setFoodPick(FoodPicks.VEGETARIAN);
      collectionOfVegetarianVotes.setVotes(new Random().nextInt(((10 - 2) + 1) + 2));
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfVegetarianVotes);

      collectionOfVeganVotes = new CollectionOfVotes();
      collectionOfVeganVotes.setLocalDate(LocalDate.now().minusDays(i));
      collectionOfVeganVotes.setFoodPick(FoodPicks.VEGAN);
      collectionOfVeganVotes.setVotes(new Random().nextInt(((10 - 2) + 1) + 2));
      votingRepositoryIMP.saveCollectionOfVotes(collectionOfVeganVotes);
    }
  }
}

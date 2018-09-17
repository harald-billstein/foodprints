package com.jayway.foodvoting.model;

import com.jayway.foodvoting.enums.FoodPicks;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CollectionOfVotes {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int ID;
  private LocalDate localDate;
  private String foodPick;
  private int votes;

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public LocalDate getLocalDate() {
    return localDate;
  }

  public void setLocalDate(LocalDate localDate) {
    this.localDate = localDate;
  }

  public String getFoodPick() {
    return foodPick;
  }

  public void setFoodPick(FoodPicks foodPick) {
    this.foodPick = foodPick.name();
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }
}

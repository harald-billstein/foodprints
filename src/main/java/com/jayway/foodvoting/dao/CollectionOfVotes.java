package com.jayway.foodvoting.dao;

import com.jayway.foodvoting.dao.Emission;
import com.jayway.foodvoting.enums.FoodPicks;
import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "collection_of_votes")
public class CollectionOfVotes {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int ID;

  private LocalDate localDate;

  @ManyToOne
  @JoinColumn(name="category", table="collection_of_votes")
  private Emission category;

  private int votes;

  public CollectionOfVotes(){}

  public CollectionOfVotes(LocalDate localDate, Emission category, int votes) {
    this.ID = ID;
    this.localDate = localDate;
    this.category = category;
    this.votes = votes;
  }

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

  public Emission getCategory() {
    return category;
  }

  public void setCategory(Emission category) {
    this.category = category;
  }

  public int getVotes() {
    return votes;
  }

  public void setVotes(int votes) {
    this.votes = votes;
  }
}

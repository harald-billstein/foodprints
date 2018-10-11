package com.jayway.foodvoting.service;

import com.jayway.foodvoting.model.CollectionOfVotes;
import com.jayway.foodvoting.model.EmissionPerMonthPerPortion;
import com.jayway.foodvoting.repository.VoteingRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StatisticsForYearService {

  private VoteingRepository voteingRepository;

  public StatisticsForYearService(VoteingRepository voteingRepository) {
    this.voteingRepository = voteingRepository;
  }

  public List<EmissionPerMonthPerPortion> getOneYearEmissionPerMonth(int year) {
    List<CollectionOfVotes> allVotes = voteingRepository.findAll();
    List<EmissionPerMonthPerPortion> statisticsList = initResponseList(year);

    for (CollectionOfVotes collectionOfVotes : allVotes) {
      addStatisticToResponse(statisticsList, collectionOfVotes, year);
    }
    return statisticsList;
  }

  private void addStatisticToResponse(
      List<EmissionPerMonthPerPortion> statisticsList, CollectionOfVotes collectionOfVotes,
      int year) {

    if (year == collectionOfVotes.getLocalDate().getYear()) {
      int month = collectionOfVotes.getLocalDate().getMonthValue() - 1;
      int totalVotes = statisticsList.get(month).getTotalVotes();
      double totalEmisson = statisticsList.get(month).getTotalEmission();

      totalVotes += collectionOfVotes.getVotes();
      totalEmisson += (co2ePerKiloToPerPortion(collectionOfVotes.getCategory().getCo2e())
          * collectionOfVotes
          .getVotes());

      statisticsList.get(month).setTotalEmission(totalEmisson);
      statisticsList.get(month).setTotalVotes(totalVotes);
    }

  }

  private double co2ePerKiloToPerPortion(double perKilo) {
    return perKilo / 8;
  }

  private List<EmissionPerMonthPerPortion> initResponseList(int year) {
    List<EmissionPerMonthPerPortion> statisticsList = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      EmissionPerMonthPerPortion emissionPerMonthPerPortion = new EmissionPerMonthPerPortion();
      emissionPerMonthPerPortion.setMonth(i);
      emissionPerMonthPerPortion.setYear(year);
      emissionPerMonthPerPortion.setTotalEmission(0);
      statisticsList.add(emissionPerMonthPerPortion);
    }
    return statisticsList;
  }
}

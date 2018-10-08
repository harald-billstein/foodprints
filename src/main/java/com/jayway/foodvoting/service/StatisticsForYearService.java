package com.jayway.foodvoting.service;

import com.jayway.foodvoting.enums.Months;
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
    List<EmissionPerMonthPerPortion> statisticsList = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      EmissionPerMonthPerPortion emissionPerMonthPerPortion = new EmissionPerMonthPerPortion();
      emissionPerMonthPerPortion.setMonth(i);
      emissionPerMonthPerPortion.setYear(year);
      emissionPerMonthPerPortion.setTotalPortions(0);
      emissionPerMonthPerPortion.setTotalEmission(0);
      statisticsList.add(emissionPerMonthPerPortion);
    }

    // TODO IM SORRY
    for (CollectionOfVotes allVote : allVotes) {

      int monthVoteWasRegister = allVote.getLocalDate().getMonthValue() - 1;
      int yearVoteWasRegister = allVote.getLocalDate().getYear();

      if (monthVoteWasRegister == Months.JANUARY.getValue() && yearVoteWasRegister == year) {
        int month = Months.JANUARY.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }

      if (monthVoteWasRegister == Months.FEBRUARY.getValue() && yearVoteWasRegister == year) {
        int month = Months.FEBRUARY.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.MARCH.getValue() && yearVoteWasRegister == year) {
        int month = Months.MARCH.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);

      }
      if (monthVoteWasRegister == Months.APRIL.getValue() && yearVoteWasRegister == year) {
        int month = Months.APRIL.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.MAY.getValue() && yearVoteWasRegister == year) {
        int month = Months.MAY.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);

      }
      if (monthVoteWasRegister == Months.JUNE.getValue() && yearVoteWasRegister == year) {
        int month = Months.JUNE.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);

      }
      if (monthVoteWasRegister == Months.JULY.getValue() && yearVoteWasRegister == year) {
        int month = Months.JULY.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.AUGUST.getValue() && yearVoteWasRegister == year) {
        int month = Months.AUGUST.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.SEPTEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.SEPTEMBER.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }

      if (monthVoteWasRegister == Months.OCTOBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.OCTOBER.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.NOVEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.NOVEMBER.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
      if (monthVoteWasRegister == Months.DECEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.DECEMBER.getValue();
        double totalEmission = statisticsList.get(month).getTotalEmission();
        int votes = statisticsList.get(month).getTotalPortions();

        votes += allVote.getVotes();
        totalEmission += co2ePerKiloToPerPortion(allVote.getCategory().getCo2e());

        statisticsList.get(month).setTotalEmission(totalEmission);
        statisticsList.get(month).setTotalPortions(votes);
      }
    }
    return statisticsList;
  }

  private double co2ePerKiloToPerPortion(double perKilo) {
    return perKilo / 8;
  }


}

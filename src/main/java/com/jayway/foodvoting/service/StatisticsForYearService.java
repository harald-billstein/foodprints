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
      emissionPerMonthPerPortion.setTotalEmission(0);
      statisticsList.add(emissionPerMonthPerPortion);
    }

    // TODO IM SORRY
    for (CollectionOfVotes allVote : allVotes) {

      int monthVoteWasRegister = allVote.getLocalDate().getMonthValue() - 1;
      int yearVoteWasRegister = allVote.getLocalDate().getYear();

      if (monthVoteWasRegister == Months.JANUARY.getValue() && yearVoteWasRegister == year) {
        int month = Months.JANUARY.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }



      if (monthVoteWasRegister == Months.FEBRUARY.getValue() && yearVoteWasRegister == year) {
        int month = Months.FEBRUARY.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }

      if (monthVoteWasRegister == Months.MARCH.getValue() && yearVoteWasRegister == year) {
        int month = Months.MARCH.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.APRIL.getValue() && yearVoteWasRegister == year) {
        int month = Months.APRIL.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.MAY.getValue() && yearVoteWasRegister == year) {
        int month = Months.MAY.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.JUNE.getValue() && yearVoteWasRegister == year) {
        int month = Months.JUNE.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.JULY.getValue() && yearVoteWasRegister == year) {
        int month = Months.JULY.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.AUGUST.getValue() && yearVoteWasRegister == year) {
        int month = Months.AUGUST.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.SEPTEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.SEPTEMBER.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.OCTOBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.OCTOBER.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.NOVEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.NOVEMBER.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
      if (monthVoteWasRegister == Months.DECEMBER.getValue() && yearVoteWasRegister == year) {
        int month = Months.DECEMBER.getValue();

        int totalVotes = statisticsList.get(month).getTotalVotes();
        double totalEmisson = statisticsList.get(month).getTotalEmission();

        totalVotes += allVote.getVotes();
        totalEmisson += (co2ePerKiloToPerPortion(allVote.getCategory().getCo2e()) * allVote.getVotes());

        statisticsList.get(month).setTotalEmission(totalEmisson);
        statisticsList.get(month).setTotalVotes(totalVotes);
      }
    }
    return statisticsList;
  }

  private double co2ePerKiloToPerPortion(double perKilo) {
    return perKilo / 8;
  }


}

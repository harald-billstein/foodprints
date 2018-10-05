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

  public List<EmissionPerMonthPerPortion> getOneYearEmission(int year) {
    List<CollectionOfVotes> allVotes = voteingRepository.findAll();
    List<EmissionPerMonthPerPortion> eList = new ArrayList<>();

    for (int i = 0; i < 12; i++) {
      EmissionPerMonthPerPortion emissionPerMonthPerPortion = new EmissionPerMonthPerPortion();
      emissionPerMonthPerPortion.setMonth(i + 1);
      emissionPerMonthPerPortion.setYear(year);
      emissionPerMonthPerPortion.setTotalPortions(0);
      emissionPerMonthPerPortion.setTotalEmission(0);
      eList.add(emissionPerMonthPerPortion);
    }

    // TODO IM SORRY
    for (CollectionOfVotes allVote : allVotes) {

      if (allVote.getLocalDate().getMonthValue() == 1 && allVote.getLocalDate().getYear() == year) {
        eList.get(0)
            .setTotalEmission((eList.get(0).getTotalEmission() + allVote.getCategory().getCo2e()));
        eList.get(0).setTotalPortions((eList.get(0).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 2 && allVote.getLocalDate().getYear() == year) {
        eList.get(1)
            .setTotalEmission(eList.get(1).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(1).setTotalPortions((eList.get(1).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 3 && allVote.getLocalDate().getYear() == year) {
        eList.get(2)
            .setTotalEmission(eList.get(2).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(2).setTotalPortions((eList.get(2).getTotalPortions() + allVote.getVotes()));

      }
      if (allVote.getLocalDate().getMonthValue() == 4 && allVote.getLocalDate().getYear() == year) {
        eList.get(3)
            .setTotalEmission(eList.get(3).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(3).setTotalPortions((eList.get(3).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 5 && allVote.getLocalDate().getYear() == year) {
        eList.get(4)
            .setTotalEmission(eList.get(4).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(4).setTotalPortions((eList.get(4).getTotalPortions() + allVote.getVotes()));

      }
      if (allVote.getLocalDate().getMonthValue() == 6 && allVote.getLocalDate().getYear() == year) {
        eList.get(5)
            .setTotalEmission((eList.get(5).getTotalEmission() + allVote.getCategory().getCo2e()));
        eList.get(5).setTotalPortions((eList.get(5).getTotalPortions() + allVote.getVotes()));

      }
      if (allVote.getLocalDate().getMonthValue() == 7 && allVote.getLocalDate().getYear() == year) {
        eList.get(6)
            .setTotalEmission(eList.get(6).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(6).setTotalPortions((eList.get(6).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 8 && allVote.getLocalDate().getYear() == year) {
        eList.get(7)
            .setTotalEmission(eList.get(7).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(7).setTotalPortions((eList.get(7).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 9 && allVote.getLocalDate().getYear() == year) {
        eList.get(8)
            .setTotalEmission(eList.get(8).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(8).setTotalPortions((eList.get(8).getTotalPortions() + allVote.getVotes()));
      }

      if (allVote.getLocalDate().getMonthValue() == 10
          && allVote.getLocalDate().getYear() == year) {
        eList.get(9)
            .setTotalEmission(eList.get(9).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(9).setTotalPortions((eList.get(9).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 11
          && allVote.getLocalDate().getYear() == year) {
        eList.get(10)
            .setTotalEmission(eList.get(10).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(10).setTotalPortions((eList.get(10).getTotalPortions() + allVote.getVotes()));
      }
      if (allVote.getLocalDate().getMonthValue() == 12
          && allVote.getLocalDate().getYear() == year) {
        eList.get(11)
            .setTotalEmission(eList.get(11).getTotalEmission() + allVote.getCategory().getCo2e());
        eList.get(11).setTotalPortions((eList.get(11).getTotalPortions() + allVote.getVotes()));
      }
    }
    return eList;
  }
}

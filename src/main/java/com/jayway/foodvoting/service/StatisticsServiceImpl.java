package com.jayway.foodvoting.service;

import com.jayway.foodvoting.dao.EmissionGoal;
import com.jayway.foodvoting.model.CategoryStatistics;
import com.jayway.foodvoting.model.CategorySum;
import com.jayway.foodvoting.model.CollectionOfVotes;
import com.jayway.foodvoting.model.Statistics;
import com.jayway.foodvoting.repository.FoodTypeRepository;
import com.jayway.foodvoting.repository.VoteingRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    // Based on köttfärs @ 125 grams.
    // https://pub.epsilon.slu.se/8710/1/roos_e_120413.pdf
    // TODO Get from database
    private final double daylyPortionGoal = 2.0;

    private VoteingRepository voteingRepository;

    private FoodTypeRepository foodTypeRepository;

    public StatisticsServiceImpl(VoteingRepository voteingRepository, FoodTypeRepository foodTypeRepository) {
        this.voteingRepository = voteingRepository;
    }

    @Override
    public EmissionGoal findGoal(LocalDate from, LocalDate to) {
        Integer portions = voteingRepository.findLocalDateBetweenAndSum(from, to);
        if (portions == null)
            return new EmissionGoal(daylyPortionGoal, null);
        // Base the emisson goal on the previous time period (week in most cases probably).
        double goal = daylyPortionGoal * portions;
        return new EmissionGoal(daylyPortionGoal, goal);
    }

    @Override
    public Statistics calculateForCategories(LocalDate from, LocalDate to) {
        Period period = from.until(to);

        Integer portions = voteingRepository.findLocalDateBetweenAndSum(from, to);
        if (portions == null)
            portions = 0;

        List<CategoryStatistics> catstat = voteingRepository.findCategoryStatisticsBetween(from, to);
        for (CategoryStatistics stat: catstat) {
            stat.setPortionsPercent((double) stat.getNumPortions() / (double) portions);
        }

        catstat =  sortResultSoFrontendLikesIt(catstat);
        catstat = transformCo2StatsFromKgToPortion(catstat);

        double totalCo2e = sumCo2e(catstat);

        Statistics stat = new Statistics(totalCo2e, portions, catstat);

        LocalDate prevFrom = from.minusDays(period.getDays());
        List<CategoryStatistics> prevStat = voteingRepository.findCategoryStatisticsBetween(prevFrom, from);
        double diff = totalCo2e - sumCo2e(prevStat);
        stat.setDifference(diff);
        return stat;
    }

    private List<CategoryStatistics> transformCo2StatsFromKgToPortion(List<CategoryStatistics> catstat) {

        for (CategoryStatistics categoryStatistics : catstat) {
            double perPortion = (categoryStatistics.getCo2e() / 8);
            categoryStatistics.setCo2e(perPortion);
        }
        return catstat;
    }

    private double sumCo2e(List<CategoryStatistics> catstat){
        double totalCo2e = 0D;
        for (CategoryStatistics stat: catstat){
            totalCo2e += stat.getCo2e();
        }
        return totalCo2e;
    }
    private List<CategoryStatistics> sortResultSoFrontendLikesIt(List<CategoryStatistics> catstat) {
        // TODO clean up this mess!

        CategoryStatistics[] frontendedList = new CategoryStatistics[6];

        CategoryStatistics beef = new CategoryStatistics();
        beef.setCategory("BEEF");

        CategoryStatistics pork = new CategoryStatistics();
        pork.setCategory("PORK");

        CategoryStatistics chicken = new CategoryStatistics();
        chicken.setCategory("CHICKEN");

        CategoryStatistics fish = new CategoryStatistics();
        fish.setCategory("FISH");

        CategoryStatistics vegetarian = new CategoryStatistics();
        vegetarian.setCategory("VEGETARIAN");

        CategoryStatistics vegan = new CategoryStatistics();
        vegan.setCategory("VEGAN");

        frontendedList[0] = beef;
        frontendedList[1] = pork;
        frontendedList[2] = chicken;
        frontendedList[3] = fish;
        frontendedList[4] = vegetarian;
        frontendedList[5] = vegan;

        for (CategoryStatistics categoryStatistics: catstat){
            for (int i = 0; i < frontendedList.length ; i++) {
                if (frontendedList[i].getCategory().equals(categoryStatistics.getCategory())){
                    frontendedList[i] = categoryStatistics;
                }
            }

        }
        return Arrays.asList(frontendedList);
    }
}





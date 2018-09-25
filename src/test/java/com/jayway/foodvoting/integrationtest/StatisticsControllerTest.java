package com.jayway.foodvoting.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.jayway.foodvoting.dao.EmissionGoal;
import com.jayway.foodvoting.model.CategoryStatistics;
import com.jayway.foodvoting.model.Statistics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsControllerTest extends IntegrationTest {

    private final static String CLIMATE_GOAL_PATH = "/v1/emission/goal";
    private final static String STATISTICS_PATH = "/v1/emission/statistics";

    private final static String DATE_FROM = "2018-09-10";
    private final static String DATE_TO = "2018-09-14";
    private final static String DATE_NO_ENTIES_BEFORE = "2010-09-03";

    private final static double DAYLY_PORTION_GOAL = 2.0;

    @Test
    public void testClimateGoal() throws Exception {
        MultiValueMap<String, String> params = createToFromParams(LocalDate.parse(DATE_FROM), LocalDate.parse(DATE_TO));

        int portions = 10;
        double oracleGoal = DAYLY_PORTION_GOAL * portions;
        expectValidResponse(mvcPerformValidGet(CLIMATE_GOAL_PATH, params), new EmissionGoal(DAYLY_PORTION_GOAL, oracleGoal), EmissionGoal.class);
    }

    @Test
    public void testClimateGoalNoFoundEntries() throws Exception {
        LocalDate to = LocalDate.parse(DATE_NO_ENTIES_BEFORE);
        MultiValueMap<String, String> params = createToFromParams(to.minusDays(5), to);

        expectValidResponse(mvcPerformValidGet(CLIMATE_GOAL_PATH, params), new EmissionGoal(DAYLY_PORTION_GOAL, null), EmissionGoal.class);
    }

    @Test
    public void testClimateGoalNoParam() throws Exception {
        expectInvalidResponse(mvcPerformValidGet(CLIMATE_GOAL_PATH), 400);
    }

    @Test
    public void testStatisticsNoParam() throws Exception{
        expectInvalidResponse(mvcPerformValidGet(STATISTICS_PATH), 400);
    }

    @Test
    public void testStatisticsValid() throws Exception{
        // double totalCo2e, int totalPortions, List<CategoryStatistics> categoryStatistics)
        final double beefCo2e = 26.0;

        List<CategoryStatistics> catstat = createCategoryStatistics(beefCo2e, "BEEF",10);
        double totalCo2e = catstat.stream().map(s -> s.getCo2e()).reduce(0D, (x, y) -> x + y);
        long totalPortions = catstat.stream().map(s -> s.getNumPortions()).reduce(0L, (x, y) -> x + y);
        calculatePercent(catstat, totalPortions);
        Statistics oracle = new Statistics(totalCo2e, (int)totalPortions, catstat);

        double lastPeriodCo2e = 208.0;
        oracle.setDifference(totalCo2e - lastPeriodCo2e);

        MultiValueMap<String, String> params = createToFromParams(LocalDate.parse(DATE_FROM), LocalDate.parse(DATE_TO));
        expectValidResponse(mvcPerformValidGet(STATISTICS_PATH, params), oracle, Statistics.class);
    }

    private List<CategoryStatistics> createCategoryStatistics(double co2e, String category, int catQuant){
        List<CategoryStatistics> catStat = new ArrayList<>();
        catStat.add(new CategoryStatistics(category, co2e * catQuant, catQuant));
        return catStat;
    }

    private List<CategoryStatistics> calculatePercent(List<CategoryStatistics> catStat, double totalPortions){
        for (CategoryStatistics stat: catStat){
            stat.setPortionsPercent(stat.getNumPortions() / totalPortions);
        }
        return catStat;
    }

    @Test
    public void testStatisticsNoFoundEntries() throws Exception{
        LocalDate to = LocalDate.parse(DATE_NO_ENTIES_BEFORE);
        MultiValueMap<String, String> params = createToFromParams(to.minusDays(5), to);
        expectValidResponse(mvcPerformValidGet(STATISTICS_PATH, params), new Statistics(), Statistics.class);
    }

    private MultiValueMap<String, String> createToFromParams(LocalDate from, LocalDate to){
        MultiValueMap<String, String> params = new HttpHeaders();
        params.put("from", Arrays.asList(from.toString()));
        params.put("to", Arrays.asList(to.toString()));
        return params;
    }

}


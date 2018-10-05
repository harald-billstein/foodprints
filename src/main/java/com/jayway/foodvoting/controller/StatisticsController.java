package com.jayway.foodvoting.controller;

import com.jayway.foodvoting.dao.EmissionGoal;
import com.jayway.foodvoting.model.EmissionPerMonthPerPortion;
import com.jayway.foodvoting.model.Statistics;
import com.jayway.foodvoting.service.StatisticsForYearService;
import com.jayway.foodvoting.service.StatisticsService;
import com.jayway.foodvoting.service.StatisticsServiceImpl;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.apache.commons.lang3.Validate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.jayway.foodvoting.model.EmissionPerMonthPerPortion;
//import com.jayway.foodvoting.service.StatisticsForYearService;

@RestController
@RequestMapping(value = "/v1/emission")
public class StatisticsController {

  private StatisticsService statisticsService;
  private StatisticsForYearService statisticsForYearService;

  public StatisticsController(StatisticsServiceImpl statisticsService,
      StatisticsForYearService statisticsForYearService) {
    this.statisticsService = statisticsService;
    this.statisticsForYearService = statisticsForYearService;
  }

  @GetMapping(path = "/goal")
  public EmissionGoal climateGoal(@RequestParam String from, @RequestParam String to) {
    Validate.notEmpty(from, "Requires from date");
    Validate.notEmpty(to, "Requires to date");

    LocalDate fromDate = LocalDate.parse(from);
    LocalDate toDate = LocalDate.parse(to);

    validateDates(fromDate, toDate);

    return statisticsService.findGoal(fromDate, toDate);
  }

  @GetMapping(path = "/statistics")
  public Statistics getStatistics(@RequestParam String from, @RequestParam String to) {
    Validate.notEmpty(from, "Requires from date");
    Validate.notEmpty(to, "Requires to date");

    LocalDate fromDate = LocalDate.parse(from);
    LocalDate toDate = LocalDate.parse(to);

    validateDates(fromDate, toDate);

    return statisticsService.calculateForCategories(fromDate, toDate);
  }


  @GetMapping(path = "/statistics/year/per/month")
  public ResponseEntity<List<EmissionPerMonthPerPortion>> getStatistics(String year) {
    if (validateYear(year)) {
      return ResponseEntity.ok(statisticsForYearService.getOneYearEmission(Integer.parseInt(year)));
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  private void validateDates(LocalDate from, LocalDate to) {
    long daysInPeriod = from.until(to, ChronoUnit.DAYS);
    if (daysInPeriod <= 0) {
      throw new IllegalArgumentException("Date to has to be later than date from.");
    }
  }

  private boolean validateYear(String year) {
    boolean valid;
    try {
      Integer.parseInt(year);
      valid = Integer.parseInt(year) > 0;
    } catch (NumberFormatException e) {
      e.getStackTrace();
      valid = false;
    }
    return valid;
  }
}

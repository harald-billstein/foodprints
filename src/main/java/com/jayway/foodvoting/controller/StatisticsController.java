package com.jayway.foodvoting.controller;

import com.jayway.foodvoting.dao.EmissionGoal;
import com.jayway.foodvoting.model.Statistics;
import com.jayway.foodvoting.service.StatisticsService;
import com.jayway.foodvoting.service.StatisticsServiceImpl;
import org.apache.commons.lang3.Validate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "/v1/emission")
public class StatisticsController {

    private StatisticsService statisticsService;

    public StatisticsController(StatisticsServiceImpl statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping(path="/goal")
    public EmissionGoal climateGoal(@RequestParam String from, @RequestParam String to){
        Validate.notEmpty(from, "Requires from date");
        Validate.notEmpty(from, "Requires to date");

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        validateDates(fromDate, toDate);

        return statisticsService.findGoal(fromDate, toDate);
    }

    @GetMapping(path = "/statistics")
    public Statistics getStatistics(@RequestParam String from, @RequestParam String to){
        Validate.notEmpty(from, "Requires from date");
        Validate.notEmpty(from, "Requires to date");

        LocalDate fromDate = LocalDate.parse(from);
        LocalDate toDate = LocalDate.parse(to);

        validateDates(fromDate, toDate);

        return statisticsService.calculateForCategories(fromDate, toDate);
    }

    private void validateDates(LocalDate from, LocalDate to){
        if (from.until(to).getDays() <= 0)
            throw new IllegalArgumentException("Date to has to be later than date from.");
    }

}

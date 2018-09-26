package com.jayway.foodvoting.service;

import com.jayway.foodvoting.dao.EmissionGoal;
import com.jayway.foodvoting.model.Statistics;

import java.time.LocalDate;

public interface StatisticsService {

    EmissionGoal findGoal(LocalDate from, LocalDate to);

    Statistics calculateForCategories(LocalDate from, LocalDate to);

}

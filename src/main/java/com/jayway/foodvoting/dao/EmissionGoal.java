package com.jayway.foodvoting.dao;

import java.util.Objects;

public class EmissionGoal {

    private Double goalCo2ePerPortion;

    // In CO2 equivalent
    private Double goalCo2e;

    public EmissionGoal(){}

    public EmissionGoal(Double goalCo2ePerPortion, Double goalCo2e) {
        this.goalCo2ePerPortion = goalCo2ePerPortion;
        this.goalCo2e = goalCo2e;
    }

    public Double getGoalCo2ePerPortion() {
        return goalCo2ePerPortion;
    }

    public void setGoalCo2ePerPortion(Double goalCo2ePerPortion) {
        this.goalCo2ePerPortion = goalCo2ePerPortion;
    }

    public Double getGoalCo2e() {
        return goalCo2e;
    }

    public void setGoalCo2e(Double goalCo2e) {
        this.goalCo2e = goalCo2e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmissionGoal that = (EmissionGoal) o;
        return Objects.equals(goalCo2e, that.goalCo2e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(goalCo2e);
    }

    @Override
    public String toString() {
        return "EmissionGoal{" +
                "goalCo2e=" + goalCo2e +
                '}';
    }
}


package com.jayway.foodvoting.dao;

import com.jayway.foodvoting.enums.FoodPicks;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="emission")
public class Emission {

    @Id
    private String category;

    // Carbon dioxide equivalent
    @Column(name = "co2e")
    private Double co2e;

    public Emission() {}

    public Emission(FoodPicks category) {
        this.category = category.name();
    }

    public FoodPicks getCategory() {
        return FoodPicks.valueOf(category);
    }

    public void setCategory(FoodPicks category) {
        this.category = category.name();
    }

    public Double getCo2e() {
        return co2e;
    }

    public void setCo2e(Double co2e) {
        this.co2e = co2e;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Emission emission = (Emission) o;
        return category == emission.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }
}

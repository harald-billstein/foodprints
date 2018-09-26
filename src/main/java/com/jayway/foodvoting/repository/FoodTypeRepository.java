package com.jayway.foodvoting.repository;

import com.jayway.foodvoting.dao.Emission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodTypeRepository extends JpaRepository<Emission, String> {

    Emission findByCategory(String category);

}

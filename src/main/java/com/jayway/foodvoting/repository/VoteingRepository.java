package com.jayway.foodvoting.repository;

import com.jayway.foodvoting.model.CategoryStatistics;
import com.jayway.foodvoting.model.CategorySum;
import com.jayway.foodvoting.model.CollectionOfVotes;
import com.jayway.foodvoting.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VoteingRepository extends JpaRepository<CollectionOfVotes, Integer> {

    List<CollectionOfVotes> findByLocalDateBetween(LocalDate from, LocalDate to);


    @Query("SELECT NEW com.jayway.foodvoting.model.CategorySum(cv.category.category, SUM(cv.votes)) " +
            "FROM CollectionOfVotes AS cv " +
            "WHERE cv.localDate BETWEEN ?1 AND ?2 " +
            "GROUP BY cv.category.category"
    )
    List<CategorySum> findCategoryVotesBetween(LocalDate from, LocalDate to);

    @Query("SELECT NEW com.jayway.foodvoting.model.CategoryStatistics(cv.category.category, cv.category.co2e * SUM(cv.votes), SUM(cv.votes)) " +
            "FROM CollectionOfVotes cv JOIN cv.category "+
            "WHERE cv.localDate BETWEEN ?1 AND ?2 " +
            "GROUP BY cv.category.category"
    )
    List<CategoryStatistics> findCategoryStatisticsBetween(LocalDate from, LocalDate to);

    @Query("SELECT SUM(cv.votes) " +
            "FROM CollectionOfVotes AS cv " +
            "WHERE cv.localDate BETWEEN ?1 AND ?2"
    )
    Integer findLocalDateBetweenAndSum(LocalDate from, LocalDate to);

}



package com.jayway.foodvoting.endpoints;

import com.jayway.foodvoting.enums.FoodPicks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/v1")
public class CategoriesEndpoint {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "/categories/")
    public ResponseEntity<List> getCategories() {
        LOGGER.info("ENDPOINT : CATEGORIES");
        return ResponseEntity.ok(Arrays.asList(FoodPicks.values()));
    }
}

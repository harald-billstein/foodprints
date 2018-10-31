package com.jayway.foodvoting.integrationtest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodvotingApplicationTests extends IntegrationTest {

	@Test
	public void testNotFoundWithValidAuth() throws Exception {
        mvc.perform( get("/nonexisting").secure( true ).with(httpBasic(VALID_USER, VALID_PASSWORD)) )
                .andExpect(status().isNotFound());
    }


    @Test
    public void testNonExistingWithInvalidAuth() throws Exception {
        mvc.perform( get("/nonexisting").secure( true ).with(httpBasic(INVALID_USER, INVALID_PASSWORD)) )
                .andExpect(status().isUnauthorized());
    }

    // TODO FIX FAILING TEST @Test
    public void testNonExistingWithAnonymousUser() throws Exception {
        mvc.perform( get("/nonexisting").secure( true ).with(anonymous()) )
                .andExpect(status().isUnauthorized());
    }

}

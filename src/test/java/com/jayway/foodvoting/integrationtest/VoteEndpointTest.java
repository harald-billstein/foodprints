package com.jayway.foodvoting.integrationtest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class VoteEndpointTest extends IntegrationTest {

  @Autowired
  private WebApplicationContext context;
  private String url = "https://localhost:8443/v1/votes/";

  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void testVoteVegan() throws Exception {
    mvcPerformValidPost(url, "vote", "VEGAN", status().isOk());
  }

  @Test
  public void testVoteVegetarian() throws Exception {
    mvcPerformValidPost(url, "vote", "VEGETARIAN", status().isOk());
  }

  @Test
  public void testVoteFish() throws Exception {
    mvcPerformValidPost(url, "vote", "FISH", status().isOk());
  }

  @Test
  public void testVoteChicken() throws Exception {
    mvcPerformValidPost(url, "vote", "CHICKEN", status().isOk());
  }

  @Test
  public void testVoteBeef() throws Exception {
    mvcPerformValidPost(url, "vote", "BEEF", status().isOk());
  }

  @Test
  public void testVotePork() throws Exception {
    mvcPerformValidPost(url, "vote", "PORK", status().isOk());
  }

  @Test
  public void testInvalidVote() throws Exception {
    mvcPerformValidPost(url, "vote", "invalidPick", status().isIAmATeapot());
  }

  @Test
  public void testEmptyVote() throws Exception {
    mvcPerformEmptyParamsFailurePost(url, status().isBadRequest());
  }

  @Test
  public void testBadCredentials() throws Exception {
    mvcPerformAuthorizationFailurePost(url, "vote", "PORK", status().isUnauthorized());
  }
}

package com.jayway.foodvoting.integrationtest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.foodvoting.model.VoteRequest;
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
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("VEGAN"));
  }

  @Test
  public void testVoteVegetarian() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("VEGETARIAN"));
  }

  @Test
  public void testVoteFish() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("FISH"));
  }

  @Test
  public void testVoteChicken() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("CHICKEN"));
  }

  @Test
  public void testVoteBeef() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("BEEF"));
  }

  @Test
  public void testVotePork() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("PORK"));
  }

  @Test
  public void testInvalidVote() throws Exception {
    mvcPerformValidPost(url, status().isIAmATeapot(), getVoteRequest("invalidPick"));
  }

  @Test
  public void testEmptyVote() throws Exception {
    mvcPerformEmptyParamsFailurePost(url, status().isBadRequest());
  }

  @Test
  public void testBadCredentials() throws Exception {
    mvcPerformAuthorizationFailurePost(url, "vote", "PORK", status().isUnauthorized());
  }

  public VoteRequest getVoteRequest(String vote) {
    VoteRequest voteRequest = new VoteRequest();
    voteRequest.setVote(vote);
    return voteRequest;
  }
}

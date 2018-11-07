package com.jayway.foodvoting.integrationtest;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.jayway.foodvoting.model.VoteRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class VoteEndpointTest extends IntegrationTest {

  @Rule
  public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();
  @Autowired
  private WebApplicationContext context;
  private String url = "https://localhost:8443/v1/votes/";

  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .apply(documentationConfiguration(this.jUnitRestDocumentation))
        .build();
  }

  @Test
  public void testVoteVegan() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("VEGAN"), "testVoteVegan");
  }

  @Test
  public void testVoteVegetarian() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("VEGETARIAN"), "testVoteVegetarian");
  }

  @Test
  public void testVoteFish() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("FISH"), "testVoteFish");
  }

  @Test
  public void testVoteChicken() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("CHICKEN"), "testVoteChicken");
  }

  @Test
  public void testVoteBeef() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("BEEF"), "testVoteBeef");
  }

  @Test
  public void testVotePork() throws Exception {
    mvcPerformValidPost(url, status().isOk(), getVoteRequest("PORK"), "testVotePork");
  }

  @Test
  public void testInvalidVote() throws Exception {
    mvcPerformValidPost(url, status().isIAmATeapot(), getVoteRequest("invalidPick"),
        "testInvalidVote");
  }

  @Test
  public void testEmptyVote() throws Exception {
    mvcPerformEmptyParamsFailurePost(url, status().isBadRequest(), "testEmptyVote");
  }

  @Test
  public void testBadCredentials() throws Exception {
    mvcPerformAuthorizationFailurePost(url, "vote", "PORK", status().isUnauthorized(),
        "testBadCredentials");
  }

  public VoteRequest getVoteRequest(String vote) {
    VoteRequest voteRequest = new VoteRequest();
    voteRequest.setVote(vote);
    return voteRequest;
  }
}

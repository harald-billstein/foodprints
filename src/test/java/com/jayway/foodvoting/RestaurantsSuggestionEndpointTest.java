package com.jayway.foodvoting;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantsSuggestionEndpointTest {

  @Autowired
  private WebApplicationContext context;
  private MockMvc mvc;
  private String url = "https://localhost:8443/v1/votes/";
  private String userName = "testuser";
  private String password = "password123";


  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  @Test
  public void testVoteVegan() throws Exception {
    mvc.perform(post(url)
        .param("vote", "VEGAN")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void testVoteVegetarian() throws Exception {
    mvc.perform(post(url)
        .param("vote", "VEGETARIAN")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void testVoteFish() throws Exception {
    mvc.perform(post(url)
        .param("vote", "FISH")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void testVoteChicken() throws Exception {
    mvc.perform(post(url)
        .param("vote", "CHICKEN")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void testVoteMeat() throws Exception {
    mvc.perform(post(url)
        .param("vote", "MEAT")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isOk());
  }

  @Test
  public void testInvaliedVote() throws Exception {
    mvc.perform(post(url)
        .param("vote", "invalidPick")
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isIAmATeapot());
  }

  @Test
  public void testEmptyVote() throws Exception {
    mvc.perform(post(url)
        .secure(true)
        .with(httpBasic(userName, password)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void testBadCredentials() throws Exception {
    mvc.perform(post(url)
        .param("vote", "MEAT")
        .secure(true)
        .with(httpBasic("invalidName", "invalidPassword")))
        .andExpect(status().isUnauthorized());
  }
}

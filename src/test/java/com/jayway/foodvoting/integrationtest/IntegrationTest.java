package com.jayway.foodvoting.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

public abstract class IntegrationTest {

  protected static final String VALID_USER = "testuser";
  protected static final String VALID_PASSWORD = "password123";

  protected static final String INVALID_USER = "invaliduser";
  protected static final String INVALID_PASSWORD = "invalidpassword123";

  @Autowired
  protected WebApplicationContext context;

  protected MockMvc mvc;


  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .build();
  }

  protected ResultActions mvcPerformValidGet(String path) throws Exception {
    return mvc.perform(get(path).secure(true).with(httpBasic(VALID_USER, VALID_PASSWORD)));
  }

  protected ResultActions mvcPerformValidGet(String path, MultiValueMap<String, String> params)
      throws Exception {
    return mvc
        .perform(get(path).secure(true).with(httpBasic(VALID_USER, VALID_PASSWORD)).params(params));
  }

  protected void mvcPerformValidPost(String path, String paramKey, String paramValue,
      ResultMatcher resultMatcher)
      throws Exception {
    mvc.perform(post(path).param(paramKey, paramValue).secure(true)
        .with(httpBasic(VALID_USER, VALID_PASSWORD))).andExpect(resultMatcher);
  }

  protected void mvcPerformAuthorizationFailurePost(String path, String paramKey, String paramValue,
      ResultMatcher resultMatcher)
      throws Exception {
    mvc.perform(post(path).param(paramKey, paramValue).secure(true)
        .with(httpBasic(INVALID_USER, INVALID_PASSWORD))).andExpect(resultMatcher);
  }

  protected void mvcPerformEmptyParamsFailurePost(String path, ResultMatcher resultMatcher)
      throws Exception {
    mvc.perform(post(path).secure(true).with(httpBasic(VALID_USER, VALID_PASSWORD)))
        .andExpect(resultMatcher);
  }

  protected <T> void expectValidResponse(ResultActions resultActions, T oracle, Class<T> clazz)
      throws Exception {
    MvcResult mvcResult = resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

    String json = mvcResult.getResponse().getContentAsString();
    T actual = new ObjectMapper().readValue(json, clazz);
    assertEquals(oracle, actual);
  }

  protected <T> void expectInvalidResponse(ResultActions resultActions, int status)
      throws Exception {
    resultActions.andExpect(status().is(status));
  }

  protected <T> void expectInvalidResponse(ResultActions resultActions, int status, T oracle,
      Class<T> clazz) throws Exception {
    MvcResult mvcResult = resultActions.andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();

    String json = mvcResult.getResponse().getContentAsString();
    T actual = new ObjectMapper().readValue(json, clazz);
    assertEquals(oracle, actual);
  }

}


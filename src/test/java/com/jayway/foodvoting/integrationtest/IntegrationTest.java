package com.jayway.foodvoting.integrationtest;

import static org.junit.Assert.assertEquals;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.foodvoting.model.VoteRequest;
import org.junit.Before;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@AutoConfigureRestDocs
public abstract class IntegrationTest {

  protected static final String VALID_USER = "testuser";
  protected static final String VALID_PASSWORD = "password123";

  protected static final String INVALID_USER = "invaliduser";
  protected static final String INVALID_PASSWORD = "invalidpassword123";
  @Rule
  public JUnitRestDocumentation jUnitRestDocumentation = new JUnitRestDocumentation();
  @Autowired
  protected WebApplicationContext context;
  protected MockMvc mvc;

  @Before
  public void setUpMockMvc() {
    mvc = MockMvcBuilders.webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity())
        .apply(documentationConfiguration(this.jUnitRestDocumentation))
        .build();
  }

  protected ResultActions mvcPerformValidGet(String path, String docIdentifier) throws Exception {
    return mvc.perform(RestDocumentationRequestBuilders
        .get(path)
        .secure(true)
        .with(httpBasic(VALID_USER, VALID_PASSWORD)))
        .andDo(document(docIdentifier));
  }

  protected ResultActions mvcPerformValidGet(String path, MultiValueMap<String, String> params,
      String docIdentifier)
      throws Exception {
    return mvc
        .perform(RestDocumentationRequestBuilders
            .get(path)
            .secure(true)
            .with(httpBasic(VALID_USER, VALID_PASSWORD))
            .params(params))
        .andDo(document(docIdentifier));
  }

  protected void mvcPerformValidPost(String path, ResultMatcher resultMatcher,
      VoteRequest voteRequest, String docIdentifier)
      throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();

    objectMapper.writeValueAsString(voteRequest);
    mvc.perform(RestDocumentationRequestBuilders
        .post(path)
        .content(objectMapper.writeValueAsString(voteRequest))
        .header("Content-type", "application/json")
        .secure(true)
        .with(httpBasic(VALID_USER, VALID_PASSWORD)))
        .andExpect(resultMatcher)
        .andDo(document(docIdentifier));
  }


  protected void mvcPerformAuthorizationFailurePost(String path, String paramKey, String paramValue,
      ResultMatcher resultMatcher, String docIdentifier)
      throws Exception {
    mvc.perform(RestDocumentationRequestBuilders
        .post(path)
        .param(paramKey, paramValue)
        .secure(true)
        .with(httpBasic(INVALID_USER, INVALID_PASSWORD)))
        .andExpect(resultMatcher)
        .andDo(document(docIdentifier));
  }

  protected void mvcPerformEmptyParamsFailurePost(String path, ResultMatcher resultMatcher,
      String docIdentifier)
      throws Exception {
    mvc.perform(RestDocumentationRequestBuilders
        .post(path)
        .secure(true)
        .with(httpBasic(VALID_USER, VALID_PASSWORD)))
        .andExpect(resultMatcher)
        .andDo(document(docIdentifier));
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


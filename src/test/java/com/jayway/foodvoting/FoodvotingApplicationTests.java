package com.jayway.foodvoting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.Filter;

import com.jayway.foodvoting.configuration.SecurityConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(profiles = {"dev"})
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodvotingApplicationTests {

    private static final String VALID_USER = "testuser";
    private static final String VALID_PASSWORD = "password123";

    private static final String INVALID_USER = "invaliduser";
    private static final String INVALID_PASSWORD = "invalidpassword123";

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;


    @Before
    public void setUpMockMvc(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

	@Test
	public void testNotFoundWithValidAuth() throws Exception {
        mvc.perform( get("/nonexisting").with(httpBasic(VALID_USER, VALID_PASSWORD)) )
                .andExpect(status().isNotFound());
    }


    @Test
    public void testNotFoundWithInvalidAuth() throws Exception { // TOOD Rename
        mvc.perform( get("/nonexisting").with(httpBasic(INVALID_USER, INVALID_PASSWORD)) )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testNotFoundWithAnonymousUser() throws Exception { // TOOD Rename
        mvc.perform( get("/nonexisting").with(anonymous()) )
                .andExpect(status().isUnauthorized());
    }

}

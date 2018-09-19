package com.jayway.foodvoting.configuration;

import com.jayway.foodvoting.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Profile(value = "dev")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigDev extends AbstractSecurityConfig {

  public SecurityConfigDev(UserService userService) {
    super(userService);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    super.configure(http);
    http.authorizeRequests().antMatchers("/h2*").permitAll()
            .and()
            .authorizeRequests().antMatchers("/console/**").permitAll();

    http.csrf().disable();
    http.headers().frameOptions().disable();
  }
}

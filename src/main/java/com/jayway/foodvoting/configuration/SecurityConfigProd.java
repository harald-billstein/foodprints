package com.jayway.foodvoting.configuration;

import com.jayway.foodvoting.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile(value = "prod")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfigProd extends AbstractSecurityConfig {

  public SecurityConfigProd(UserService userService) {
    super(userService);
  }
}

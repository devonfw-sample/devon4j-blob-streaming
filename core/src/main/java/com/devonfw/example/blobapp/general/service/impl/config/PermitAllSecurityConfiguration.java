package com.devonfw.example.blobapp.general.service.impl.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * This configuration configures spring security in a way that all requests are permitted.
 *
 *
 */
@Configuration
@EnableWebSecurity
public class PermitAllSecurityConfiguration extends WebSecurityConfigurerAdapter {
  @Override
  public void configure(WebSecurity web) {

    web.ignoring().antMatchers("/**");
  }
}

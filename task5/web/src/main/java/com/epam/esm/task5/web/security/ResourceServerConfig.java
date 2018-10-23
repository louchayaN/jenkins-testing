package com.epam.esm.task5.web.security;

import com.epam.esm.task5.service.dto.ResponseMessage;
import com.epam.esm.task5.web.security.custom.CustomWebResponseExceptionTranslator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

  private static final String QUESTS_PATH = "/quests/**";
  private static final String TAGS_PATH = "/tags/**";
  private static final String RESERVATIONS_PATH = "/reservations/**";
  private static final String USER_PATH = "/users/**";

  private static final String ADMIN = "ADMIN";
  private static final String OWNER = "OWNER";
  private static final String USER = "USER";

  @Bean
  public WebResponseExceptionTranslator<OAuth2Exception> webResponseExceptionTranslator() {
    return new DefaultWebResponseExceptionTranslator();
  }

  @Bean
  public WebResponseExceptionTranslator<ResponseMessage> customExceptionTranslator() {
    return new CustomWebResponseExceptionTranslator();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint() {
    OAuth2AuthenticationEntryPoint entryPoint = new OAuth2AuthenticationEntryPoint();
    entryPoint.setExceptionTranslator(customExceptionTranslator());
    return entryPoint;
  }

  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    OAuth2AccessDeniedHandler handler = new OAuth2AccessDeniedHandler();
    handler.setExceptionTranslator(customExceptionTranslator());
    return handler;
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources
        .accessDeniedHandler(accessDeniedHandler())
        .authenticationEntryPoint(authenticationEntryPoint());
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(HttpMethod.POST, USER_PATH)
        .anonymous()
        .antMatchers(HttpMethod.POST, QUESTS_PATH)
        .hasAnyRole(OWNER)
        .antMatchers(HttpMethod.PUT, QUESTS_PATH)
        .hasAnyRole(OWNER)
        .antMatchers(HttpMethod.DELETE, QUESTS_PATH)
        .hasAnyRole(OWNER)
        .antMatchers(HttpMethod.GET, TAGS_PATH)
        .hasAnyRole(USER, OWNER, ADMIN)
        .antMatchers(HttpMethod.POST, TAGS_PATH)
        .hasAnyRole(OWNER, ADMIN)
        .antMatchers(HttpMethod.DELETE, TAGS_PATH)
        .hasRole(ADMIN)
        .antMatchers(HttpMethod.POST, RESERVATIONS_PATH)
        .hasRole(USER)
        .antMatchers(HttpMethod.GET, RESERVATIONS_PATH)
        .hasAnyRole(USER, OWNER, ADMIN)
        .antMatchers(HttpMethod.DELETE, RESERVATIONS_PATH)
        .hasAnyRole(USER, OWNER)
        .antMatchers(HttpMethod.OPTIONS)
        .permitAll();
  }
}

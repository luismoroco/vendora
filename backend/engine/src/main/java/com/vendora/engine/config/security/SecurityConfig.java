package com.vendora.engine.config.security;

import com.vendora.engine.config.security.filters.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  private static final String[] PUBLIC_ROUTES = {"/api/v1/products/**", "/api/v1/categories/**"};
  private static final String[] GRAPH_QL = {"/graphql"};
  private static final String[] AUTH_ROUTES = {"/api/v1/auth/**"};
  private static final String[] PRIVATE_ROUTES = {"/api/v1/**"};
  private static final String[] WEBHOOK_ROUTES = {"/api/v1/payments/stripe/complete/**"};
  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  public SecurityConfig(
    JwtAuthFilter jwtAuthFilter,
    AuthenticationProvider authenticationProvider
  ) {
    this.jwtAuthFilter = jwtAuthFilter;
    this.authenticationProvider = authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests
        (authorize -> authorize
          //.requestMatchers(HttpMethod.POST, AUTH_ROUTES).permitAll()
          //.requestMatchers(HttpMethod.GET, PUBLIC_ROUTES).permitAll()
          //.requestMatchers(HttpMethod.POST, WEBHOOK_ROUTES).permitAll()
          //.requestMatchers(HttpMethod.GET, GRAPH_QL).permitAll()
          //.requestMatchers(HttpMethod.POST, GRAPH_QL).permitAll()
          //.requestMatchers(PRIVATE_ROUTES).authenticated()
          .anyRequest().permitAll()
        )
      .sessionManagement
        (manager -> manager
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(this.authenticationProvider)
      .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .cors(Customizer.withDefaults())
      .build();
  }
}


package com.vendora.engine.config.security;

import com.vendora.engine.config.filters.JwtAuthFilter;
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
  private final JwtAuthFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;

  private static final String[] PUBLIC_ROUTES = { "/api/v1/products/**" };
  private static final String[] AUTH_ROUTES = { "/api/v1/auth/**" };

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
          .requestMatchers(HttpMethod.POST, AUTH_ROUTES).permitAll()
          .requestMatchers(HttpMethod.GET, PUBLIC_ROUTES).permitAll()
          .anyRequest().authenticated())
      .sessionManagement
        (manager -> manager
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authenticationProvider(this.authenticationProvider)
      .addFilterBefore(this.jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
      .cors(Customizer.withDefaults())
      .build();
  }
}


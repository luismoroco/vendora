package com.vendora.engine.config.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vendora.engine.common.cache.CacheService;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.error.model.ApiError;
import com.vendora.engine.common.session.exc.InvalidJwtException;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.common.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;
  private final CacheService cacheService;
  private final JwtService jwtService;
  private final ObjectMapper mapper;

  public JwtAuthFilter(
    UserDetailsService userDetailsService,
    CacheService cacheService,
    JwtService jwtService,
    ObjectMapper mapper
  ) {
    this.userDetailsService = userDetailsService;
    this.cacheService = cacheService;
    this.jwtService = jwtService;
    this.mapper = mapper;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    var token = JwtService.getBearerToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    if (token.isEmpty()) {
      filterChain.doFilter(request, response);

      return;
    }

    try {
      var username = this.jwtService.getSubject(token.get());
      this.cacheService.get(CacheTopic.SESSION, username, SessionUser.class)
        .orElseThrow(
          () -> new ServletException("Token not found")
        );

      if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
        var user = this.userDetailsService.loadUserByUsername(username);

        if (this.jwtService.isValid(token.get(), user.getUsername())) {
          var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      }

      filterChain.doFilter(request, response);
    } catch (InvalidJwtException e) {
      this.handleError(response, HttpStatus.UNAUTHORIZED, e);
    } catch (RuntimeException e) {
      handleError(response, HttpStatus.INTERNAL_SERVER_ERROR, e);
    }
  }

  private void handleError(
    HttpServletResponse response,
    HttpStatus status,
    RuntimeException e
  ) throws IOException {
    response.setStatus(status.value());
    response.setContentType("application/json");

    var error = new ApiError(e);
    var jsonResponse = mapper.writeValueAsString(error);

    response.getWriter().write(jsonResponse);
    response.getWriter().flush();
  }
}

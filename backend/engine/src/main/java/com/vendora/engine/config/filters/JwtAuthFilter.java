package com.vendora.engine.config.filters;

import com.vendora.engine.common.cache.CacheProxy;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.common.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
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
  private final CacheProxy cacheService;

  public JwtAuthFilter(
    UserDetailsService userDetailsService,
    CacheProxy cacheService
  ) {
    this.userDetailsService = userDetailsService;
    this.cacheService = cacheService;
  }

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    var token = JwtUtil.getHeaderToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    if (token.isEmpty()) {
      filterChain.doFilter(request, response);

      return;
    }

    var username = JwtUtil.getSubject(token.get());
    this.cacheService.get(CacheTopic.SESSION, username, SessionUser.class)
      .orElseThrow(
        () -> new ServletException("Token not found")
      );

    if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
      var user = this.userDetailsService.loadUserByUsername(username);

      if (JwtUtil.isValid(token.get(), user.getUsername())) {
        var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);
      }
    }

    filterChain.doFilter(request, response);
  }
}

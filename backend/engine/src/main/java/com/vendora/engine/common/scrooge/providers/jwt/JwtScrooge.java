package com.vendora.engine.common.scrooge.providers.jwt;

import com.vendora.engine.common.cache.CacheService;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.error.exc.exception.UnauthorizedException;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.common.util.JwtService;
import com.vendora.engine.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Jwt")
public class JwtScrooge implements Scrooge<JwtCredentials> {
  private final CacheService cacheService;
  private final JwtService jwtService;

  private UserDetails user;
  private SecurityContext context;

  public JwtScrooge(
    CacheService cacheService,
    JwtService jwtService)
  {
    this.cacheService = cacheService;
    this.jwtService = jwtService;
  }

  @Override
  public void setContext() {
    this.context = SecurityContextHolder.getContext();
    this.user = (UserDetails) this.context.getAuthentication().getPrincipal();
  }

  @Override
  public <U extends User> JwtCredentials generateKeys(U u) {
    var token = this.jwtService.buildToken(u.getUsername());
    var session = SessionUser.builder()
      .userId(u.getUserId())
      .userType(u.getUserType())
      .email(u.getEmail())
      .build();

    this.cacheService.put(CacheTopic.SESSION, u.getUsername(), session);
    return new JwtCredentials(token);
  }

  @Override
  public void destroyKeys() {
    this.cacheService.evict(CacheTopic.SESSION, this.user.getUsername());
    this.context.setAuthentication(null);
  }

  @Override
  public SessionUser retrieveKeys() {
    return this.cacheService.get(CacheTopic.SESSION, this.user.getUsername(), SessionUser.class)
      .orElseThrow(
        () -> new UnauthorizedException("Session not found")
      );
  }
}

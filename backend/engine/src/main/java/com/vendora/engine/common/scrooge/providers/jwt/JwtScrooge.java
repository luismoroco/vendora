package com.vendora.engine.common.scrooge.providers.jwt;

import com.vendora.engine.common.cache.CacheProxy;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.error.exc.exception.UnauthorizedException;
import com.vendora.engine.common.scrooge.providers.Scrooge;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.common.util.JwtUtil;
import com.vendora.engine.modules.user.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Jwt")
public class JwtScrooge implements Scrooge<JwtCredentials> {
  private final CacheProxy cacheProxy;

  private UserDetails user;
  private SecurityContext context;

  public JwtScrooge(
    CacheProxy cacheProxy
  ) {
    this.cacheProxy = cacheProxy;
  }

  @Override
  public void setContext() {
    this.context = SecurityContextHolder.getContext();
    this.user = (UserDetails) this.context.getAuthentication().getPrincipal();
  }

  @Override
  public <U extends User> JwtCredentials generateKeys(U u) {
    var token = JwtUtil.buildToken(u.getUsername());
    var session = SessionUser.builder()
      .userId(u.getUserId())
      .userType(u.getUserType())
      .email(u.getEmail())
      .build();

    this.cacheProxy.put(CacheTopic.SESSION, u.getUsername(), session);
    return new JwtCredentials(token);
  }

  @Override
  public void destroyKeys() {
    this.cacheProxy.evict(CacheTopic.SESSION, this.user.getUsername());
    this.context.setAuthentication(null);
  }

  @Override
  public SessionUser retrieveKeys() {
    return this.cacheProxy.get(CacheTopic.SESSION, this.user.getUsername(), SessionUser.class)
      .orElseThrow(
        () -> new UnauthorizedException("Session not found")
      );
  }
}

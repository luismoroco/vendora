package com.vendora.engine.security.cerberus.engines;

import com.vendora.engine.cache.CacheProxy;
import com.vendora.engine.cache.model.CacheTopic;
import com.vendora.engine.common.exc.exception.VendoraUnauthorizedException;
import com.vendora.engine.common.util.JwtUtil;
import com.vendora.engine.modules.auth.model.Session;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.security.cerberus.Cerberus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Jwt")
public class JwtCerberus implements Cerberus<Session, JwtCerberus> {
  private final CacheProxy cacheProxy;

  private UserDetails user;
  private SecurityContext context;

  public JwtCerberus(
    CacheProxy cacheProxy
  ) {
    this.cacheProxy = cacheProxy;
  }

  @Override
  public JwtCerberus context() {
    this.context = SecurityContextHolder.getContext();
    this.user = (UserDetails) this.context.getAuthentication().getPrincipal();

    return this;
  }

  @Override
  public <U extends User> Session generateKeys(U u) {
    var token = JwtUtil.buildToken(u.getUsername());
    var session = Session.builder()
      .token(token)
      .userId(u.getUserId())
      .userType(u.getUserType())
      .build();

    this.cacheProxy.put(CacheTopic.SESSION, u.getUsername(), session);
    return session;
  }

  @Override
  public void destroyKeys() {
    this.cacheProxy.evict(CacheTopic.SESSION, this.user.getUsername());
    this.context.setAuthentication(null);
  }

  @Override
  public Session retrieveKeys() {
    return this.cacheProxy.get(CacheTopic.SESSION, this.user.getUsername(), Session.class)
      .orElseThrow(
        () -> new VendoraUnauthorizedException("Session not found")
      );
  }
}

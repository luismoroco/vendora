package com.vendora.engine.security.cerberus.engines;

import com.vendora.engine.cache.CacheProxy;
import com.vendora.engine.cache.model.CacheTopic;
import com.vendora.engine.common.exc.exception.VendoraUnauthorizedException;
import com.vendora.engine.common.session.model.SessionUser;
import com.vendora.engine.common.util.JwtUtil;
import com.vendora.engine.modules.user.model.User;
import com.vendora.engine.security.cerberus.Cerberus;
import com.vendora.engine.security.cerberus.credentials.types.JwtCredentials;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Qualifier("Jwt")
public class JwtCerberus implements Cerberus<JwtCredentials> {
  private final CacheProxy cacheProxy;

  private UserDetails user;
  private SecurityContext context;

  public JwtCerberus(
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
        () -> new VendoraUnauthorizedException("Session not found")
      );
  }
}

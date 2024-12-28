package com.vendora.engine.security.authorization;

import com.vendora.engine.cache.CacheGateway;
import com.vendora.engine.cache.model.CacheTopic;
import com.vendora.engine.common.exc.exception.UnauthorizedException;
import com.vendora.engine.common.util.JwtUtil;
import com.vendora.engine.modules.auth.model.Session;
import com.vendora.engine.modules.user.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {
  private final CacheGateway cacheService;
  private UserDetails userDetails;

  public SessionManager(
    CacheGateway cacheService
  ) {
    this.cacheService = cacheService;
  }

  public Session getSession() {
    this.userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    var v = this.cacheService.get(CacheTopic.SESSION, this.userDetails.getUsername(), Session.class)
      .orElseThrow(
        () -> new UnauthorizedException("Session not found")
      );

    return v;
  }

  public void destroy() {
    this.cacheService.evict(CacheTopic.SESSION, this.userDetails.getUsername());
    SecurityContextHolder.getContext().setAuthentication(null);
  }

  public  Session createSession(User user) {
    var token = JwtUtil.buildToken(user.getUsername());
    var session = new Session(token, user);

    this.cacheService.put(CacheTopic.SESSION, user.getUsername(), session);
    return session;
  }
}

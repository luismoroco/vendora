package com.vendora.engine.modules.notification.event.notification_event.consumer.solvers;

import com.vendora.engine.common.cache.CacheService;
import com.vendora.engine.common.cache.model.CacheTopic;
import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.common.jwt.JwtService;
import com.vendora.engine.common.mail.EmailService;
import com.vendora.engine.common.mail.request.EmailRequest;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import com.vendora.engine.modules.user.UserUseCase;
import com.vendora.engine.modules.user.request.GetUserByIdRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component("password_recovery")
public class PasswordRecoverySolver implements EventSolver<NotificationEvent> {
  @Value("${application.host.url}")
  private String hostUrl;

  private final EmailService emailService;
  private final UserUseCase userUseCase;
  private final JwtService jwtService;
  private final CacheService cacheService;

  public PasswordRecoverySolver(
    EmailService emailService,
    UserUseCase userUseCase,
    JwtService jwtService,
    CacheService cacheService
  ) {
    this.emailService = emailService;
    this.userUseCase = userUseCase;
    this.jwtService = jwtService;
    this.cacheService = cacheService;
  }

  @Override
  public void execute(NotificationEvent event) {
    var user = this.userUseCase.getUserById(new GetUserByIdRequest(event.getUserId()));
    var token = this.jwtService.buildToken(user.getEmail());
    var recoveryUrl = UriComponentsBuilder.fromUriString(this.hostUrl)
      .path("/api/v1/auth/password-recovery")
      .queryParam("token", token)
      .build()
      .toUriString();

    this.cacheService.put(CacheTopic.PASSWORD_RECOVERY, user.getEmail(), recoveryUrl);
    this.emailService.send(
      EmailRequest.builder()
        .to(user.getEmail())
        .subject(event.getNotificationEventType().getSubject())
        .message("Hi, %s. Please, follow this link for recover your password: %s".formatted(user.getFirstName(), recoveryUrl))
        .build()
    );
  }
}

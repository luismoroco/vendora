package com.vendora.engine.modules.notification.event.notification_event.consumer.solvers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.common.mail.EmaiService;
import com.vendora.engine.common.mail.request.EmailRequest;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import com.vendora.engine.modules.user.UserUseCase;
import com.vendora.engine.modules.user.request.GetUserByIdRequest;
import org.springframework.stereotype.Component;

@Component("password_recovery")
public class PasswordRecoverySolver implements EventSolver<NotificationEvent> {
  private final EmaiService emaiService;
  private final UserUseCase userUseCase;

  public PasswordRecoverySolver(EmaiService emaiService, UserUseCase userUseCase) {
    this.emaiService = emaiService;
    this.userUseCase = userUseCase;
  }

  @Override
  public void execute(NotificationEvent event) {
    var user = this.userUseCase.getUserById(new GetUserByIdRequest(event.getUserId()));
    this.emaiService.send(
      EmailRequest.builder()
        .from("lmorocoramos@gmail.com")
        .to(user.getEmail())
        .subject(event.getNotificationEventType().name())
        .message("Recupera tu contrasenia gaaa oe que")
        .build()
    );
  }
}

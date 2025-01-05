package com.vendora.engine.modules.notification.event.notification_event.consumer.solvers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import org.springframework.stereotype.Component;

@Component("password_recovery")
public class PasswordRecoverySolver implements EventSolver<NotificationEvent> {
  @Override
  public void execute(NotificationEvent event) {
    System.out.printf("Executing %s%n", event.getNotificationEventType());
  }
}

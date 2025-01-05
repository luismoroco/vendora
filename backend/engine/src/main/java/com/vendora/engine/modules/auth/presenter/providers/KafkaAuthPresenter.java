package com.vendora.engine.modules.auth.presenter.providers;

import com.vendora.engine.modules.auth.presenter.AuthPresenter;
import com.vendora.engine.modules.notification.event.NotificationEventType;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import com.vendora.engine.modules.notification.event.notification_event.emitter.NotificationEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaAuthPresenter implements AuthPresenter {
  private final NotificationEventEmitter notificationEventEmitter;

  public KafkaAuthPresenter(@Qualifier("kafka") NotificationEventEmitter notificationEventEmitter) {
    this.notificationEventEmitter = notificationEventEmitter;
  }

  @Override
  public void notifyPasswordRecovery(Long userId) {
    this.notificationEventEmitter.emit(
      NotificationEvent.builder()
        .notificationEventType(NotificationEventType.PASSWORD_RECOVERY)
        .userId(userId)
        .build()
    );
  }
}

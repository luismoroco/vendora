package com.vendora.engine.modules.notification.event.notification_event.consumer.providers;

import com.vendora.engine.common.event.EventSolver;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import com.vendora.engine.modules.notification.event.notification_event.consumer.NotificationEventConsumer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Qualifier("kafka")
public class KafkaNotificationEventConsumer implements NotificationEventConsumer {
  private final Map<String, EventSolver<NotificationEvent>> solverMap;

  public KafkaNotificationEventConsumer(Map<String, EventSolver<NotificationEvent>> solverMap) {
    this.solverMap = solverMap;
  }

  @Override
  @KafkaListener(topics = "NOTIFICATION", groupId = "1")
  public void consume(NotificationEvent event) {
    var solver = this.solverMap.get(event.getNotificationEventType().name().toLowerCase());
    solver.execute(event);
  }
}

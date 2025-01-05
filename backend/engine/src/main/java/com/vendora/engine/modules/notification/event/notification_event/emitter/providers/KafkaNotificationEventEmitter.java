package com.vendora.engine.modules.notification.event.notification_event.emitter.providers;

import com.vendora.engine.kafka.model.KafkaTopic;
import com.vendora.engine.modules.notification.event.notification_event.NotificationEvent;
import com.vendora.engine.modules.notification.event.notification_event.emitter.NotificationEventEmitter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Qualifier("kafka")
public class KafkaNotificationEventEmitter implements NotificationEventEmitter {
  private static final String TOPIC = KafkaTopic.NOTIFICATION.name();
  private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

  public KafkaNotificationEventEmitter(KafkaTemplate<String, NotificationEvent> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @Override
  public void emit(NotificationEvent event) {
    this.kafkaTemplate.send(TOPIC, event);
  }
}

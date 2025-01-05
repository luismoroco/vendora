package com.vendora.engine.modules.notification.event.notification_event;

import com.vendora.engine.common.event.Event;
import com.vendora.engine.modules.notification.event.NotificationEventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent extends Event {
  private Long userId;
  private NotificationEventType notificationEventType;
}

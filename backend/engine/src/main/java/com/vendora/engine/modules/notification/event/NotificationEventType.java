package com.vendora.engine.modules.notification.event;

import lombok.Getter;

@Getter
public enum NotificationEventType {
  PASSWORD_RECOVERY("Password Recovery"),
  LOW_STOCK("Low Stock"),;

  final String subject;

  NotificationEventType(String subject) {
    this.subject = subject;
  }
}

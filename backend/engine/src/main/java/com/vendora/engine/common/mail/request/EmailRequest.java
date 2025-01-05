package com.vendora.engine.common.mail.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
  private String from;
  private String to;
  private String subject;
  private String message;
}

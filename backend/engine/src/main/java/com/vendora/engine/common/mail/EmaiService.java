package com.vendora.engine.common.mail;

import com.vendora.engine.common.mail.request.EmailRequest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmaiService {
  private final JavaMailSender mailSender;

  public EmaiService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void send(EmailRequest request) {
    var message = new SimpleMailMessage();
    message.setFrom(request.getFrom());
    message.setTo(request.getTo());
    message.setSubject(request.getSubject());
    message.setText(request.getMessage());

    this.mailSender.send(message);
  }
}

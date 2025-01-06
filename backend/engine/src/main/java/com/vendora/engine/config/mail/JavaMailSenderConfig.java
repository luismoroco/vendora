package com.vendora.engine.config.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {
  @Value("${spring.mail.username}")
  private String from;

  @Value("${spring.mail.password}")
  private String password;

  @Bean
  public JavaMailSender javaMailSender() {
    var sender = new JavaMailSenderImpl();
    sender.setHost("smtp.gmail.com");
    sender.setPort(587);
    sender.setUsername(this.from);
    sender.setPassword(this.password);

    var properties = sender.getJavaMailProperties();
    properties.put("mail.transport.protocol", "smtp");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.debug", "true");

    return sender;
  }
}

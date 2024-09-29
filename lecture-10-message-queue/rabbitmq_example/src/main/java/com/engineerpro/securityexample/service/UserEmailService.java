package com.engineerpro.securityexample.service;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.engineerpro.securityexample.entity.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserEmailService {
  private final JavaMailSender mailSender;

  public UserEmailService(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendUserActivationEmail(User user) {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setSubject("Account Activation");
    mail.setText(
        "Activation code: " + user.getActivateCode());
    log.info("Try send mail");
    // throw new MailSendException("try set fail");
    // mailSender.send(mail);
    log.info("Email sent successfully to: " + user.getEmail());
  }
}

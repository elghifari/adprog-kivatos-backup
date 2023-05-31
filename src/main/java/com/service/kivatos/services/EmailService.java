package com.service.kivatos.services;

import javax.mail.MessagingException;

public interface EmailService {
    public void sendEmail(String to, String subject, String body) throws MessagingException;
}

package com.learning.ecommerceapi.service;


import jakarta.mail.MessagingException;
import org.thymeleaf.context.Context;

public interface EmailService {
    void sendEmailWithHtmlTemplate(String toEmail, String subject, String templateName, Context context) throws MessagingException;
}

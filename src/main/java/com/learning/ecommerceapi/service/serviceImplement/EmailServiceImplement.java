package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class EmailServiceImplement implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Override
    public void sendEmailWithHtmlTemplate(String toEmail, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
        try {
            helper.setFrom("kroemvandy71@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);

            // process thymeleaf template
            String htmlContent = templateEngine.process(templateName, context);
            helper.setText(htmlContent, true);

            // Add the inline image, referenced from the HTML code as "cid:logo"
            ClassPathResource logo = new ClassPathResource("template/logo.png");
            helper.addInline("logo", logo);

            //sent the email
            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new MessagingException("Failed to send email", e);
        }

    }
}
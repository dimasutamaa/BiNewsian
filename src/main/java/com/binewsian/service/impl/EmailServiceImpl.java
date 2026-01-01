package com.binewsian.service.impl;

import com.binewsian.exception.BiNewsianException;
import com.binewsian.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Override
    public void sendCredentials(String email, String username, String rawPassword) throws BiNewsianException {
        log.info("Sending credentials email to {}", email);

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "UTF-8"
            );

            Context context = new Context();
            context.setVariable("username", username);
            context.setVariable("password", rawPassword);

            String htmlContent = templateEngine.process(
                    "email/account-created",
                    context
            );

            helper.setTo(email);
            helper.setSubject("Your Contributor Account Credentials");
            helper.setText(htmlContent, true);

            mailSender.send(message);

            log.info("Email successfully sent to {}", email);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", email, e);
            throw new BiNewsianException("Failed to send credentials email");
        }
    }

    @Override
    public void sendResetPassword(String email, String token, String appUrl) throws BiNewsianException {
        log.info("Sending reset password email to {}", email);

        try {
            String resetUrl = appUrl + "/reset-password?token=" + token;

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    "UTF-8"
            );

            Context context = new Context();
            context.setVariable("email", email);
            context.setVariable("resetUrl", resetUrl);

            String htmlContent = templateEngine.process(
                    "email/password-reset",
                    context
            );

            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText(htmlContent, true);

            mailSender.send(message);

            log.info("Email successfully sent to {}", email);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}", email, e);
            throw new BiNewsianException("Failed to send reset password email");
        }
    }

}

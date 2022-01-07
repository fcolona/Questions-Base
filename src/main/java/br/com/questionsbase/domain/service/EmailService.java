package br.com.questionsbase.domain.service;

import java.util.Locale;

import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import br.com.questionsbase.domain.model.User;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@EnableAsync
public class EmailService {
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = "Click the link below to reset your password. If you did not ask for the password reset, ignore this email";
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    @Async
    public SimpleMailMessage constructEmail(String subject, String body, User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getEmail());
        email.setFrom(env.getProperty("spring.mail.username"));
        return email;
    }
}

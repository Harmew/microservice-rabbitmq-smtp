package com.harmew.ms.email.services;

import com.harmew.ms.email.enums.StatusEmail;
import com.harmew.ms.email.models.EmailModel;
import com.harmew.ms.email.repositories.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    private final EmailRepository emailRepository;
    private final JavaMailSender javaMailSender;

    public EmailService(EmailRepository emailRepository, JavaMailSender javaMailSender) {
        this.emailRepository = emailRepository;
        this.javaMailSender = javaMailSender;
    }

    @Value("${spring.mail.username}")
    private String emailFrom;


    @Transactional
    public void sendEmail(EmailModel emailModel) {
        try {
            emailModel.setSendDateEmail(LocalDateTime.now());
            emailModel.setEmailFrom(emailFrom);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(emailModel.getEmailTo());
            mailMessage.setSubject(emailModel.getSubject());
            mailMessage.setText(emailModel.getText());
            javaMailSender.send(mailMessage);

            emailModel.setStatusEmail(StatusEmail.SENT);
            save(emailModel);
        } catch (MailException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            save(emailModel);
        }
    }

    private void save(EmailModel emailModel) {
        emailRepository.save(emailModel);
    }
}

package com.amitgroup.services.email;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.amitgroup.sqldatabase.entities.User;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
@Log4j2
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private MailProperties properties;

    @Autowired
    private JavaMailSender mailSender;

    private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,
            Integer.MAX_VALUE, 10, TimeUnit.MINUTES, new LinkedBlockingQueue<>());

    public void sendSimpleMessage(String to, String subject, String body) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        emailSender.send(message);
    }

    public void sendEmail(String toEmail, String subject, String body) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(body, true);
        mailSender.send(message);
    }

    public void sendResetPasswordEmail(User user, String resetLink) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(user.getEmail());
            helper.setSubject("Reset Password");
            helper.setText("Please click on the link below to reset your password: " + resetLink, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    public void sendSimpleMessage(String to, String subject, String body, boolean async) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body, true);
        if (async) {
            this.addSendTask(message);
        } else {
            emailSender.send(message);
        }
    }

    public void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("File", file);

        emailSender.send(message);
    }

    public void sendMessageWithAttachment(String to, String subject, String body, File attachFile)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource file = new FileSystemResource(attachFile);
        helper.addAttachment("File", file);

        emailSender.send(message);
    }

    public void sendMessageWithAttachment(String to, String subject, String body, String pathToAttachment,
            boolean async) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(properties.getUsername());
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
        helper.addAttachment("File", file);
        if (async) {
            this.addSendTask(message);
        } else {
            emailSender.send(message);
        }
    }

    private void addSendTask(final MimeMessage mimeMessage) {
        try {
            pool.execute(() -> {
                emailSender.send(mimeMessage);
                log.debug("Send successfully !");
            });
        } catch (Exception e) {
            log.error("Exception when send email !", e);
        }
    }

}

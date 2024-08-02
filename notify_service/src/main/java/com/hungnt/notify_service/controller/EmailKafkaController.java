package com.hungnt.notify_service.controller;

import com.hungnt.notify_service.dto.request.MailRequest;
import com.hungnt.notify_service.service.MailService;
import event.UserEvent;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class EmailKafkaController {
    @Autowired
    private MailService mailService;

    // Mail Service
    @KafkaListener(topics = "sendEmail")
    public void sendEmail(UserEvent userEvent) throws MessagingException {
        log.info("Email will be sent to: " + userEvent.getEmail() + ", please check your email.");
        MailRequest mailRequest = new MailRequest();

        mailRequest.setTo(userEvent.getEmail());
        mailRequest.setSubject("XAC NHAN THONG TIN NGUOI DUNG");

        Map<String, Object> properties = new HashMap<>();

        properties.put("firstName", userEvent.getFirstName());
        properties.put("lastName", userEvent.getLastName());
        properties.put("username", userEvent.getUsername());

        mailRequest.setProperties(properties);

        mailService.sendMail(mailRequest, "templateEmail");
    }
}

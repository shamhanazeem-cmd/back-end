package com.edu.Institiute.api;

import com.edu.Institiute.utill.other.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private EmailSender emailSender;

    @GetMapping("/test-email")
    public String testEmail() {
        emailSender.sendSimpleEmail(
                "chathurangabdr@gmail.com",
                "Test Email",
                "This is a test email from your application"
        );
        return "Email sent!";
    }
}

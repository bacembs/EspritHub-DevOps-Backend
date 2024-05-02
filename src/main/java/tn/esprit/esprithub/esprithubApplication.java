package tn.esprit.esprithub;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import tn.esprit.esprithub.services.InternshipService;

@SpringBootApplication
public class esprithubApplication {
    @Autowired
    private InternshipService internshipService;
    public static void main(String[] args) {
        SpringApplication.run(esprithubApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void triggerMail() throws MessagingException {
        internshipService.sendSimpleEmail("arij.khedhira@esprit.tn",
                "This is email body",
                "This is email subject");
    }

}

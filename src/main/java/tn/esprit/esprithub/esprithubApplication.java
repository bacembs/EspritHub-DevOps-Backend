package tn.esprit.esprithub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class esprithubApplication {

    public static void main(String[] args) {
        SpringApplication.run(esprithubApplication.class, args);
    }

}

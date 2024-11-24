package fr.elvis.chatop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("fr.elvis.chatop.configuration")
public class ChatopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatopApplication.class, args);
    }

}

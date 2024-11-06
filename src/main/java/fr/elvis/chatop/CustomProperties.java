package fr.elvis.chatop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "fr.elvis.chatop.front")
public class CustomProperties {
    private String apiUrl;
}

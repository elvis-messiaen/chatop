package fr.elvis.chatop.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.logging.Logger;

@RestController
public class BackendUrlController {
    private Logger logger = Logger.getLogger(BackendUrlController.class.getName());

    @GetMapping("/api/backend-url")
    public String getBackendUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        String backendUrl = scheme + "://" + serverName + ":" + serverPort + contextPath;
        logger.info("Backend URL: " + backendUrl);
        return backendUrl;
    }
}

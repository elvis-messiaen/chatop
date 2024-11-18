package fr.elvis.chatop.security.auth;

public class AuthDTO {
    public record LoginRequest(String username, String password) {
    }

    public record Response(String message, String token) {
    }

    public record RegisterRequest(String username, String password, String email) {
    }
}

package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.RegisterRequestDTO;
import fr.elvis.chatop.DTO.ResponseDTO;
import fr.elvis.chatop.DTO.UserResponseDTO;
import fr.elvis.chatop.DTO.AuthDTO.TokenResponse;
import fr.elvis.chatop.servicies.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private UserAuthService authService;

    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Enregistre un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @io.swagger.v3.oas.annotations.media.Content),
            @ApiResponse(responseCode = "409", description = "Le nom d'utilisateur ou l'email est déjà pris.", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequestDTO registerRequest) {
        if (registerRequest.getUsername() == null || registerRequest.getUsername().trim().isEmpty() ||
                registerRequest.getEmail() == null || registerRequest.getEmail().trim().isEmpty() ||
                registerRequest.getPassword() == null || registerRequest.getPassword().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{}");
        }

        try {
            String token = authService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.OK).body(new TokenResponse(token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseDTO("Conflict"));
        }
    }

    @Operation(summary = "Déconnecter l'utilisateur", description = "Déconnecte l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @io.swagger.v3.oas.annotations.media.Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        SecurityContextHolder.clearContext();
        ResponseDTO response = new ResponseDTO("Utilisateur déconnecté avec succès");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtenir les informations de l'utilisateur authentifié", description = "Renvoie les informations de l'utilisateur actuellement authentifié.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")
            }),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "L'utilisateur n'est pas trouvé.")
    })
    @GetMapping("/me")
    public ResponseEntity<?> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserResponseDTO userResponse = authService.getUserInfo(username);
        if (userResponse != null) {
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{}"); // Empty JSON body
        }
    }
}
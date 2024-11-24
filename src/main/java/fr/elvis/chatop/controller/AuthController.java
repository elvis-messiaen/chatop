package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.RegisterRequestDTO;
import fr.elvis.chatop.DTO.ResponseDTO;
import fr.elvis.chatop.DTO.UserResponseDTO;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.UserRepository;
import fr.elvis.chatop.servicies.UserAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthService authService;

    @Operation(summary = "Enregistrer un nouvel utilisateur", description = "Enregistre un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "L'utilisateur est enregistré."),
            @ApiResponse(responseCode = "400", description = "Les données fournies ne sont pas valides."),
            @ApiResponse(responseCode = "409", description = "Le nom d'utilisateur ou l'email est déjà pris.")
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO registerRequest) {
        try {
            authService.register(registerRequest);
            ResponseDTO response = new ResponseDTO("Utilisateur enregistré avec succès", null);
            return ResponseEntity.status(201).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(409).body(new ResponseDTO(e.getMessage(), null));
        }
    }

    @Operation(summary = "Déconnecter l'utilisateur", description = "Déconnecte l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur est déconnecté avec succès."),
    })
    @PostMapping("/logout")
    public ResponseEntity<ResponseDTO> logout() {
        SecurityContextHolder.clearContext();
        ResponseDTO response = new ResponseDTO("Utilisateur déconnecté avec succès", null);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtenir les informations de l'utilisateur authentifié", description = "Renvoie les informations de l'utilisateur actuellement authentifié.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les informations de l'utilisateur sont renvoyées."),
            @ApiResponse(responseCode = "404", description = "L'utilisateur n'est pas trouvé.")
    })
    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> me() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<UserEntity> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserEntity userEntity = userOptional.get();
            UserResponseDTO userResponse = new UserResponseDTO();
            userResponse.setUsername(userEntity.getUsername());
            userResponse.setEmail(userEntity.getEmail());
            userResponse.setCreatedAt(userEntity.getCreatedAt());
            userResponse.setUpdatedAt(userEntity.getUpdatedAt());
            return ResponseEntity.ok(userResponse);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}

package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.LoginRequestDTO;
import fr.elvis.chatop.DTO.LoginResponseDTO;
import fr.elvis.chatop.servicies.JWTService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    public LoginController(JWTService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Operation(summary = "Authentifie l'utilisateur", description = "Authentifie l'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "L'utilisateur est connecté."),
            @ApiResponse(responseCode = "403", description = "Les informations d'identification de l'utilisateur ne sont pas valides."),
            @ApiResponse(responseCode = "400", description = "Le nom d'utilisateur ou le mot de passe n'est pas fourni.")
    })
    @PostMapping("/login")
    public LoginResponseDTO getToken(@RequestBody LoginRequestDTO loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtService.generateToken(authentication);
        return new LoginResponseDTO(token, username);
    }
}

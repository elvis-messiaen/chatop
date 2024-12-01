package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.UserDTO;
import fr.elvis.chatop.servicies.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Tag(name = "Gestion des Utilisateurs", description = "APIs REST liées à l'entité Utilisateur")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère une liste de tous les utilisateurs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les utilisateurs récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))}),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/user")
    @PreAuthorize ("hasAuthority('ADMIN')")
    public List<UserDTO> getUser() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Obtenir l'utilisateur par ID", description = "Récupère un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Optional<UserDTO> getUserById(@PathVariable int id) {
        return Optional.of(userService.getUserById(id));
    }

    @Operation(summary = "Créer un nouvel utilisateur", description = "Crée un nouvel utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Utilisateur créé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PostMapping("/user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return userService.saveUser(userDTO);
    }

    @Operation(summary = "Mettre à jour l'utilisateur", description = "Met à jour un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur mis à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public UserDTO updateUser(@PathVariable int id, @RequestBody UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @Operation(summary = "Supprimer l'utilisateur", description = "Supprime un utilisateur par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur supprimé avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @DeleteMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
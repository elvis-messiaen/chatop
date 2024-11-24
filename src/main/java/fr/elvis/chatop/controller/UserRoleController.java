package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.RoleDTO;
import fr.elvis.chatop.servicies.UserRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

@RestController
@RequestMapping("/api")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @Operation(summary = "Obtenir les rôles de l'utilisateur", description = "Récupère les rôles associés à un utilisateur par son nom d'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les rôles de l'utilisateur ont été récupérés avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @GetMapping("/user/{username}/roles")
    public Set<RoleDTO> getUserRoles(@PathVariable String username) {
        return userRoleService.getUserRoles(username);
    }

    @Operation(summary = "Mettre à jour les rôles de l'utilisateur", description = "Met à jour les rôles associés à un utilisateur par son nom d'utilisateur")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Les rôles de l'utilisateur ont été mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    @PostMapping("/user/{username}/roles")
    public void updateUserRoles(@PathVariable String username, @RequestBody List<RoleDTO> roleDTOS) {
        Set<RoleDTO> roleDTOSet = new HashSet<>(roleDTOS);
        userRoleService.updateUserRoles(username, roleDTOSet);
    }
}

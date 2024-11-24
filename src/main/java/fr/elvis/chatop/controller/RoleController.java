package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.RoleDTO;
import fr.elvis.chatop.servicies.RoleService;
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
@Tag(name = "Gestion des Rôles", description = "APIs REST liées à l'entité Rôle")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Obtenir tous les rôles", description = "Récupère une liste de tous les rôles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les rôles récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RoleDTO.class)))}),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/roles")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<RoleDTO> getAllRoles() {
        return roleService.getAllRoles();
    }

    @Operation(summary = "Obtenir le rôle par ID", description = "Récupère un rôle par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rôle trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Rôle non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/role/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Optional<RoleDTO> getRoleById(@PathVariable int id) {
        return Optional.of(roleService.getRoleById(id));
    }

    @Operation(summary = "Créer un nouveau rôle", description = "Crée un nouveau rôle")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rôle créé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PostMapping("/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleDTO createRole(@RequestBody RoleDTO roleDTO) {
        return roleService.saveRole(roleDTO);
    }

    @Operation(summary = "Mettre à jour un rôle", description = "Met à jour un rôle par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rôle mis à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RoleDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Rôle non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PutMapping("/role/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RoleDTO updateRole(@PathVariable int id, @RequestBody RoleDTO roleDTO) {
        return roleService.updateRole(id, roleDTO);
    }

    @Operation(summary = "Supprimer un rôle", description = "Supprime un rôle par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rôle supprimé avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rôle non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @DeleteMapping("/role/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRole(@PathVariable int id) {
        roleService.dissociateUsersFromRole(id);
        roleService.deleteRole(id);
    }

}

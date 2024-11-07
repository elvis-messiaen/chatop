package fr.elvis.chatop.controller;

import fr.elvis.chatop.entities.User;
import fr.elvis.chatop.servicies.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Contrôleur Utilisateur", description = "APIs REST liées à l'entité Utilisateur")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Bienvenue Utilisateur", description = "Retourne un message de bienvenue pour l'utilisateur")
    @GetMapping("/user")
    public String getUser() {
        return "Bienvenue, Utilisateur";
    }

    @Operation(summary = "Obtenir l'utilisateur par ID", description = "Récupère un utilisateur par son ID", tags = { "utilisateurs" })
    @GetMapping("/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Mettre à jour l'utilisateur", description = "Met à jour un utilisateur par son ID", tags = { "utilisateurs" })
    @PutMapping("/user/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public User updateUser(@PathVariable int id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @Operation(summary = "Obtenir tous les utilisateurs", description = "Récupère une liste de tous les utilisateurs", tags = { "administration" })
    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Supprimer l'utilisateur", description = "Supprime un utilisateur par son ID", tags = { "administration" })
    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}

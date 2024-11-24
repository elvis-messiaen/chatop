package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.MessageDTO;
import fr.elvis.chatop.servicies.MessagesService;
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
@Tag(name = "Gestion des Messages", description = "APIs REST liées à l'entité Message")
public class MessagesController {

    @Autowired
    private MessagesService messageService;

    @Operation(summary = "Obtenir tous les messages", description = "Récupère une liste de tous les messages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de tous les messages récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MessageDTO.class)))}),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/message")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<MessageDTO> getAllMessages() {
        return messageService.getAllMessages();
    }

    @Operation(summary = "Obtenir le message par ID", description = "Récupère un message par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Message non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/message/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Optional<MessageDTO> getMessageById(@PathVariable int id) {
        return Optional.of(messageService.getMessageById(id));
    }

    @Operation(summary = "Créer un nouveau message", description = "Crée un nouveau message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message créé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PostMapping("/message")
    @PreAuthorize("hasAuthority('ADMIN')")
    public MessageDTO createMessage(@RequestBody MessageDTO messageDTO) {
        return messageService.saveMessage(messageDTO);
    }

    @Operation(summary = "Mettre à jour un message", description = "Met à jour un message par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message mis à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Message non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PutMapping("/message/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public MessageDTO updateMessage(@PathVariable int id, @RequestBody MessageDTO messageDTO) {
        return messageService.updateMessage(id, messageDTO);
    }

    @Operation(summary = "Supprimer un message", description = "Supprime un message par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message supprimé avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Message non trouvé", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @DeleteMapping("/message/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteMessage(@PathVariable int id) {
        messageService.deleteMessage(id);
    }
}

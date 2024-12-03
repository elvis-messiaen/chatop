package fr.elvis.chatop.controller;

import fr.elvis.chatop.DTO.RentalDTO;
import fr.elvis.chatop.servicies.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Tag(name = "Gestion des Locations", description = "APIs REST liées à l'entité Location")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Obtenir toutes les locations", description = "Récupère une liste de toutes les locations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Liste de toutes les locations récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = RentalDTO.class)))}),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/rentals")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Operation(summary = "Obtenir la location par ID", description = "Récupère une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content)
    })
    @GetMapping("/rentals/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> getRentalById(@PathVariable int id) {
        Optional<RentalDTO> rentalDTO = rentalService.getRentalById(id);
        if (rentalDTO.isPresent()) {
            return ResponseEntity.ok(rentalDTO.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(""); // Empty body
        }
    }

    @Operation(summary = "Créer une nouvelle location", description = "Crée une nouvelle location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location créée avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PostMapping("/rentals")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> createRental(@RequestBody RentalDTO rentalDTO) {
        rentalService.saveRental(rentalDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental created !");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Mettre à jour une location", description = "Met à jour une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PutMapping("/rentals/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<?> updateRental(@PathVariable int id, @RequestBody RentalDTO rentalDTO) {
        rentalService.updateRental(id, rentalDTO);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Rental updated !");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Supprimer une location", description = "Supprime une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location supprimée avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @DeleteMapping("/rentals/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }
}
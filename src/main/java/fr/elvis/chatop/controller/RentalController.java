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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
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
    @GetMapping("/rental")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public List<RentalDTO> getAllRentals() {
        return rentalService.getAllRentals();
    }

    @Operation(summary = "Obtenir la location par ID", description = "Récupère une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location trouvée",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @GetMapping("/rental/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public Optional<RentalDTO> getRentalById(@PathVariable int id) {
        return Optional.of(rentalService.getRentalById(id));
    }

    @Operation(summary = "Créer une nouvelle location", description = "Crée une nouvelle location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location créée avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Requête invalide", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PostMapping("/rental")
    @PreAuthorize("hasAuthority('ADMIN')")
    public RentalDTO createRental(@RequestBody RentalDTO rentalDTO) {
        return rentalService.saveRental(rentalDTO);
    }

    @Operation(summary = "Mettre à jour une location", description = "Met à jour une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location mise à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RentalDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @PutMapping("/rental/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public RentalDTO updateRental(@PathVariable int id, @RequestBody RentalDTO rentalDTO) {
        return rentalService.updateRental(id, rentalDTO);
    }


    @Operation(summary = "Supprimer une location", description = "Supprime une location par son ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location supprimée avec succès", content = @Content),
            @ApiResponse(responseCode = "404", description = "Location non trouvée", content = @Content),
            @ApiResponse(responseCode = "403", description = "Accès refusé", content = @Content)
    })
    @DeleteMapping("/rental/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }
}

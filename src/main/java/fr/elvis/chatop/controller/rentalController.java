package fr.elvis.chatop.controller;

import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.servicies.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Contrôleur Location", description = "APIs REST liées à l'entité Location")
public class rentalController {

        @Autowired
        private RentalService rentalService;

        @Operation(summary = "Obtenir une location par ID", description = "Récupère une location par son ID")
        @GetMapping("{id}")
        public Optional<Rental> getRentalById(@PathVariable int id) {
            return rentalService.getRentalById(id);
        }

        @Operation(summary = "Obtenir toutes les locations", description = "Récupère une liste de toutes les locations")
        @GetMapping
        public List<Rental> getAllRentals() {
            return rentalService.getAllRentals();
        }

        @Operation(summary = "Créer une location", description = "Crée une nouvelle location", tags = { "administration" })
        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public Rental createRental(@RequestBody Rental rental) {
            return rentalService.saveRental(rental);
        }

        @Operation(summary = "Mettre à jour une location", description = "Met à jour une location par son ID", tags = { "utilisateurs", "administration" })
        @PutMapping("/{id}")
        @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
        public Rental updateRental(@PathVariable int id, @RequestBody Rental rental) {
            return rentalService.updateRental(id, rental);
        }

        @Operation(summary = "Supprimer une location", description = "Supprime une location par son ID", tags = { "administration" })
        @DeleteMapping("/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public void deleteRental(@PathVariable int id) {
            rentalService.deleteRental(id);
        }
    }


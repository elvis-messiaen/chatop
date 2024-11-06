package fr.elvis.chatop.controller;

import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.servicies.RentalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rentals")
public class rentalController {

    private RentalService rentalService;

    @GetMapping("{id}")
    public Optional<Rental> getRentalById(@PathVariable int id) {
        return rentalService.getRentalById(id);
    }

    @GetMapping
    public List<Rental> getAllRentals() {
        return rentalService.getAllRentals();
    }
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Rental createRental(@RequestBody Rental rental) {
        return rentalService.saveRental(rental); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Rental updateRental(@PathVariable int id, @RequestBody Rental rental) {
        return rentalService.updateRental(id, rental); }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRental(@PathVariable int id) {
        rentalService.deleteRental(id);
    }


}

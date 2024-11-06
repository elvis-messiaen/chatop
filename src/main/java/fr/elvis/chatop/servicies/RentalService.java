package fr.elvis.chatop.servicies;
import org.springframework.beans.factory.annotation.Autowired;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.repository.RentalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Optional<Rental> getRentalById(int id) {
        return rentalRepository.findById(id);
    }

    public List<Rental> getAllRentals() {
        return (List<Rental>) rentalRepository.findAll();
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental); }

    public Rental updateRental(int id, Rental rental) {
        if (rentalRepository.existsById(id)) {
            rental.setId(id);
            return rentalRepository.save(rental);
        } else {
            throw new RuntimeException("Rental not found");
        }
    }

    public void deleteRental(int id) {
        rentalRepository.deleteById(id);
    }
}

package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.RentalDTO;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.repository.RentalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RentalDTO> getAllRentals() {
        return StreamSupport.stream(rentalRepository.findAll().spliterator(), false)
                .map(rental -> modelMapper.map(rental, RentalDTO.class))
                .collect(Collectors.toList());
    }

    public RentalDTO getRentalById(int id) {
        Rental rental = rentalRepository.findById(id).orElseThrow(() -> new RuntimeException("Rental not found"));
        return modelMapper.map(rental, RentalDTO.class);
    }

    public RentalDTO saveRental(RentalDTO rentalDTO) {
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
        rental = rentalRepository.save(rental);
        return modelMapper.map(rental, RentalDTO.class);
    }

    public RentalDTO updateRental(int id, RentalDTO rentalDTO) {
        if (rentalRepository.existsById(id)) {
            Rental rental = modelMapper.map(rentalDTO, Rental.class);
            rental.setId(id);
            rental = rentalRepository.save(rental);
            return modelMapper.map(rental, RentalDTO.class);
        } else {
            throw new RuntimeException("Rental not found");
        }
    }

    public void deleteRental(int id) {
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
        } else {
            throw new RuntimeException("Rental not found");
        }
    }
}

package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.RentalDTO;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.repository.RentalRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        Iterable<Rental> rentals = rentalRepository.findAll();
        List<RentalDTO> rentalDTOList = StreamSupport.stream(rentals.spliterator(), false)
                .map(rental -> {
                    RentalDTO rentalDTO = new RentalDTO();
                    rentalDTO.setId(rental.getId());
                    rentalDTO.setName(rental.getName());
                    rentalDTO.setSurface(rental.getSurface());
                    rentalDTO.setPrice(rental.getPrice());
                    rentalDTO.setPicture(rental.getPicture());
                    rentalDTO.setDescription(rental.getDescription());
                    rentalDTO.setCreatedAt(rental.getCreated_at());
                    rentalDTO.setUpdatedAt(rental.getUpdated_at());
                    return rentalDTO;
                })
                .collect(Collectors.toList());
        return rentalDTOList;
    }

    public Optional<RentalDTO> getRentalById(int id) {
        return rentalRepository.findById(id)
                .map(rental -> {
                    RentalDTO rentalDTO = new RentalDTO();
                    rentalDTO.setId(rental.getId());
                    rentalDTO.setName(rental.getName());
                    rentalDTO.setSurface(rental.getSurface());
                    rentalDTO.setPrice(rental.getPrice());
                    rentalDTO.setPicture(rental.getPicture());
                    rentalDTO.setDescription(rental.getDescription());
                    rentalDTO.setCreatedAt(rental.getCreated_at());
                    rentalDTO.setUpdatedAt(rental.getUpdated_at());
                    return rentalDTO;
                });
    }

    public RentalDTO saveRental(RentalDTO rentalDTO) {
        Rental rental = modelMapper.map(rentalDTO, Rental.class);
        rental = rentalRepository.save(rental);
        RentalDTO savedRentalDTO = modelMapper.map(rental, RentalDTO.class);
        return savedRentalDTO;
    }

    public RentalDTO updateRental(int id, RentalDTO rentalDTO) {
        if (rentalRepository.existsById(id)) {
            Rental rental = modelMapper.map(rentalDTO, Rental.class);
            rental.setId(id);
            rental = rentalRepository.save(rental);
            RentalDTO updatedRentalDTO = modelMapper.map(rental, RentalDTO.class);
            return updatedRentalDTO;
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
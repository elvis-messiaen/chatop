package fr.elvis.chatop.repository;

import fr.elvis.chatop.entities.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends CrudRepository<Rental, Integer> {
    boolean existsByName(String name);
}

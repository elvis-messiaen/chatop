package fr.elvis.chatop.repository;

import fr.elvis.chatop.entities.MessagesEntity;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessagesRepository extends CrudRepository<MessagesEntity, Integer> {

    List<MessagesEntity> findAllByRentalId(int id);
}

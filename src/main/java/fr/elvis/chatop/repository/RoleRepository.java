package fr.elvis.chatop.repository;

import fr.elvis.chatop.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    boolean existsByName(String name);

    Optional<Role> findByName(String name);
}

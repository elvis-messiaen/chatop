package fr.elvis.chatop.repository;

import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    @Query("SELECT (COUNT(u) > 0) FROM UserEntity u WHERE u.username = :username")
    boolean existsByUsername(String username);

    @Query("SELECT (COUNT(u) > 0) FROM UserEntity u WHERE u.email = :email")
    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    @Query("SELECT r FROM UserEntity u JOIN u.role r WHERE u.username = :username")
    Set<Role> findRolesByUsername(String username);
}

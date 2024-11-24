package fr.elvis.chatop.servicies;

import fr.elvis.chatop.entities.MessagesEntity;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.MessagesRepository;
import fr.elvis.chatop.repository.RentalRepository;
import fr.elvis.chatop.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessagesRepository messageRepository;

    @Async
    public CompletableFuture<Void> sauvegarderUtilisateurs(List<UserEntity> utilisateurs) {
        utilisateurs.forEach(utilisateur -> {
            if (!userRepository.existsByEmail(utilisateur.getEmail())) {
                userRepository.save(utilisateur);
            }
        });
        return CompletableFuture.completedFuture(null);
    }

    @Async
    public CompletableFuture<List<Rental>> obtenirLocations() {
        List<Rental> locations = (List<Rental>) rentalRepository.findAll();
        return CompletableFuture.completedFuture(locations);
    }

    @Async
    public CompletableFuture<List<UserEntity>> obtenirUtilisateurs() {
        List<UserEntity> utilisateurs = (List<UserEntity>) userRepository.findAll();
        return CompletableFuture.completedFuture(utilisateurs);
    }

    @Async
    public CompletableFuture<Void> sauvegarderLocations(List<Rental> locations) {
        locations.forEach(location -> {
            if (!rentalRepository.existsByName(location.getName())) {
                rentalRepository.save(location);
            }
        });
        return CompletableFuture.completedFuture(null);
    }

    @Async
    @Transactional
    public CompletableFuture<Void> sauvegarderMessages(List<MessagesEntity> messages) {
        List<MessagesEntity> validMessages = messages.stream()
                .filter(message -> message.getMessage() != null && !message.getMessage().isEmpty())
                .collect(Collectors.toList());

        validMessages.forEach(message -> {
            try {
                messageRepository.save(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return CompletableFuture.completedFuture(null);
    }
}

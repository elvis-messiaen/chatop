package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.UserDTO;
import fr.elvis.chatop.entities.Role;
import fr.elvis.chatop.entities.Rental;
import fr.elvis.chatop.entities.MessagesEntity;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.MessagesRepository;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.RentalRepository;
import fr.elvis.chatop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private MessagesRepository messageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(userEntity, UserDTO.class);
    }

    public UserDTO saveUser(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());

        Set<Role> roles = userDTO.getRole().stream()
                .map(roleDTO -> roleRepository.findById(roleDTO.getId())
                        .orElseThrow(() -> new RuntimeException("Role not found")))
                .collect(Collectors.toSet());
        userEntity.setRole(roles);

        Set<Rental> rentals = userDTO.getRentals().stream()
                .map(rentalDTO -> {
                    Rental rental = rentalRepository.findById(rentalDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Rental not found"));
                    rental.setCreated_at(new Date());
                    rental.setUpdated_at(new Date());
                    return rental;
                })
                .collect(Collectors.toSet());
        userEntity.setRentals(rentals);

        Set<MessagesEntity> messages = userDTO.getMessages().stream()
                .map(messageDTO -> {
                    MessagesEntity messageEntity = messageRepository.findById(messageDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Message not found"));
                    messageEntity.setRental(rentalRepository.findById(messageDTO.getRental().getId())
                            .orElseThrow(() -> new RuntimeException("Rental not found")));
                    messageEntity.setCreatedAt(new Date());
                    messageEntity.setUpdatedAt(new Date());
                    return messageEntity;
                }).collect(Collectors.toSet());
        userEntity.setMessages(messages);

        userEntity = userRepository.save(userEntity);
        return modelMapper.map(userEntity, UserDTO.class);
    }





    public UserDTO updateUser(int id, UserDTO userDTO) {
        if (userRepository.existsById(id)) {
            UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);

            Set<Role> roles = userDTO.getRole().stream()
                    .map(roleDTO -> {
                        return roleRepository.findById(roleDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Role not found"));
                    }).collect(Collectors.toSet());
            userEntity.setRole(roles);

            Set<Rental> rentals = userDTO.getRentals().stream()
                    .map(rentalDTO -> {
                        return rentalRepository.findById(rentalDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Rental not found"));
                    }).collect(Collectors.toSet());
            userEntity.setRentals(rentals);

            Set<MessagesEntity> messages = userDTO.getMessages().stream()
                    .map(messageDTO -> {
                        MessagesEntity messageEntity = messageRepository.findById(messageDTO.getId())
                                .orElseThrow(() -> new RuntimeException("Message not found"));
                        messageEntity.setRental(rentalRepository.findById(messageDTO.getRental().getId())
                                .orElseThrow(() -> new RuntimeException("Rental not found")));
                        return messageEntity;
                    }).collect(Collectors.toSet());
            userEntity.setMessages(messages);

            userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userEntity.setId(id);
            userEntity = userRepository.save(userEntity);

            return modelMapper.map(userEntity, UserDTO.class);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(int id) {
        if (userRepository.existsById(id)) {
            UserEntity userEntity = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            userEntity.getMessages().forEach(message -> {
                messageRepository.deleteById(message.getId());
            });
            userEntity.getRentals().forEach(rental -> {
                List<MessagesEntity> messages = messageRepository.findAllByRentalId(rental.getId());
                messages.forEach(message -> {
                    message.setRental(null);
                    messageRepository.save(message);
                });
                rentalRepository.deleteById(rental.getId());
            });
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }



    public boolean hasRole(String userName, String roleName) {
        String query = "SELECT COUNT(ur) FROM UserEntity u JOIN u.role ur WHERE u.username = :userName AND ur.name = :roleName";

        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("userName", userName)
                .setParameter("roleName", roleName)
                .getSingleResult();

        return count > 0;
    }
}

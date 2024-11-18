package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.UserDTO;
import fr.elvis.chatop.entities.Role;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new RuntimeException("Le nom d'utilisateur est déjà pris");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        userEntity.getRole().add(role);

        userRepository.save(userEntity);
    }
}

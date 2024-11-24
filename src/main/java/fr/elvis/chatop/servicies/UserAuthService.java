package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.RegisterRequestDTO;
import fr.elvis.chatop.entities.Role;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserAuthService {

    private static final Logger log = LoggerFactory.getLogger(UserAuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequestDTO userDTO) {
        log.info("Tentative d'enregistrement de l'utilisateur : {}", userDTO.getUsername());

        if (userRepository.existsByUsername(userDTO.getUsername())) {
            log.error("Le nom d'utilisateur {} est déjà pris", userDTO.getUsername());
            throw new RuntimeException("Le nom d'utilisateur est déjà pris");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            log.error("L'email {} est déjà pris", userDTO.getEmail());
            throw new RuntimeException("L'email est déjà pris");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        userEntity.setCreatedAt(new Date());
        userEntity.setUpdatedAt(new Date());

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        userEntity.setRole(roles);

        userRepository.save(userEntity);
        log.info("Utilisateur {} enregistré avec succès", userDTO.getUsername());
    }
}


package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.RoleDTO;
import fr.elvis.chatop.entities.Role;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;

@Service
public class UserRoleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Set<RoleDTO> getUserRoles(String username) {
        return userRepository.findRolesByUsername(username).stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .collect(Collectors.toSet());
    }

    public void updateUserRoles(String username, Set<RoleDTO> roleDTOS) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Set<Integer> roleIds = roleDTOS.stream().map(RoleDTO::getId).collect(Collectors.toSet());
        Set<Role> roles = new HashSet<>();
        roleRepository.findAllById(roleIds).forEach(roles::add);

        user.setRole(roles);
        userRepository.save(user);
    }
}

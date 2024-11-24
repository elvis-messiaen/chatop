package fr.elvis.chatop.servicies;

import fr.elvis.chatop.DTO.RoleDTO;
import fr.elvis.chatop.entities.Role;
import fr.elvis.chatop.entities.UserEntity;
import fr.elvis.chatop.repository.RoleRepository;
import fr.elvis.chatop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<RoleDTO> getAllRoles() {
        return StreamSupport.stream(roleRepository.findAll().spliterator(), false)
                .map(role -> modelMapper.map(role, RoleDTO.class))
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(int id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        return modelMapper.map(role, RoleDTO.class);
    }

    public RoleDTO saveRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role = roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    public RoleDTO updateRole(int id, RoleDTO roleDTO) {
        if (roleRepository.existsById(id)) {
            Role role = modelMapper.map(roleDTO, Role.class);
            role.setId(id);
            role = roleRepository.save(role);
            return modelMapper.map(role, RoleDTO.class);
        } else {
            throw new RuntimeException("Role not found");
        }
    }

    public void deleteRole(int id) {
        if (roleRepository.existsById(id)) {
            dissociateUsersFromRole(id);
            roleRepository.deleteById(id);
        } else {
            throw new RuntimeException("Role not found");
        }
    }

    public void dissociateUsersFromRole(int roleId) {
        List<UserEntity> users = (List<UserEntity>) userRepository.findAll();
        for (UserEntity user : users) {
            user.getRole().removeIf(role -> role.getId() == roleId);
            userRepository.save(user);
        }
    }
}

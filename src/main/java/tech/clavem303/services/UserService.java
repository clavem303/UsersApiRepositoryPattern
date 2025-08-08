package tech.clavem303.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import tech.clavem303.DTOs.UserDTO;
import tech.clavem303.models.User;
import tech.clavem303.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User(userDTO.name(), userDTO.email());

        userRepository.persist(user);

        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.listAll()
                .stream()
                .map(user -> new UserDTO(user.getId(), user.getName(), user.getEmail()))
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

}

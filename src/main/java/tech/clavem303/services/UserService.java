package tech.clavem303.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import tech.clavem303.DTOs.NotificationDTO;
import tech.clavem303.DTOs.UserDTO;
import tech.clavem303.models.User;
import tech.clavem303.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    @RestClient
    NotificationService notificationService;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User(userDTO.name(), userDTO.age(), userDTO.email());

        userRepository.persist(user);

        sendUserNotification("UserCreated", user.getName());

        return toDTO(user);
    }

    @Transactional
    public List<UserDTO> getAllUsers() {
        return userRepository.listAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id);
        return toDTO(user);
    }

    @Transactional
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return toDTO(user);
    }

    @Transactional
    public UserDTO patchUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id); // Busca no banco.
        if (user == null) return null; // Se não encontrar retorna null.
        user.updateFromDTO(userDTO); // Se encontrar, atualiza de acordo com dto.
        sendUserNotification("UserUpdated", user.getName());
        return toDTO(user); // retorna um novo dto com o user atualizado.
    }

    @Transactional
    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id);
        if (user == null) return false;
        userRepository.delete(user);
        sendUserNotification("UserDeleted", user.getName());
        return true;
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getAge(), user.getEmail());
    }

    private void sendUserNotification(String event, String eventName) {
        notificationService.sendNotification(new NotificationDTO(event, eventName));
    }


}

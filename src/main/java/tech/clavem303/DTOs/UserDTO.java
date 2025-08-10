package tech.clavem303.DTOs;

public record UserDTO(
        Long id,
        String name,
        Integer age,
        String email
) {
}

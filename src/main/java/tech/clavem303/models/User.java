package tech.clavem303.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tech.clavem303.DTOs.UserDTO;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String email;

    public User(String name, Integer age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void updateFromDTO(UserDTO dto) {
        if (dto.name() != null && !dto.name().isBlank()) {
            this.name = dto.name();
        }
        if (dto.age() != null && dto.age() > 0) {
            this.age = dto.age();
        }
        if (dto.email() != null && dto.email().contains("@")) {
            this.email = dto.email();
        }
    }

}

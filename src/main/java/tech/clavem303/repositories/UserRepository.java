package tech.clavem303.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import tech.clavem303.models.User;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

    public User findByEmail(String email) {
        return find("email", email).firstResult();
    }
}

package ua.iamiluxa.timetrackingspringproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.iamiluxa.timetrackingspringproject.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

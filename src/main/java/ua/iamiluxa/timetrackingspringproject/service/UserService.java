package ua.iamiluxa.timetrackingspringproject.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.iamiluxa.timetrackingspringproject.dto.RegistrationDTO;
import ua.iamiluxa.timetrackingspringproject.entity.Activity;
import ua.iamiluxa.timetrackingspringproject.entity.Authority;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.repository.UserRepo;

import javax.persistence.NoResultException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo,
                       PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        @NonNull
        User user = userRepo.findByUsername(s);

        return user;
    }

    public List<User> getAllUsers() {return userRepo.findAll(Sort.sort(User.class)); }

    public User getUserById(long id) {
        return userRepo.findById(id).orElseThrow(() ->
                new NoResultException("User id not found: " + id));
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        List<Activity> userActivity = user.getActivities();
        for (Activity activity : userActivity) {
            activity.getUsers().remove(user);
        }
        userRepo.delete(user);
    }

    public void registerUser(RegistrationDTO registrationDTO) {
        User user = User.builder()
                        .firstName(registrationDTO.getFirstName())
                        .lastName(registrationDTO.getLastName())
                        .username(registrationDTO.getUsername())
                        .password(passwordEncoder.encode(registrationDTO.getPassword()))
                        .enabled(true)
                        .authorities(Collections.singleton(Authority.USER))
                        .build();
        userRepo.save(user);
    }



}

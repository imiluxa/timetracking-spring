package ua.iamiluxa.timetrackingspringproject.service;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.iamiluxa.timetrackingspringproject.entity.User;
import ua.iamiluxa.timetrackingspringproject.repository.UserRepo;

@Log4j2
@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;


    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        @NonNull
        User user = userRepo.findByUsername(s);

        return user;
    }

}

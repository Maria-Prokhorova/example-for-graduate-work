package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.register.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserDetailsManager manager,
                           PasswordEncoder passwordEncoder, UserRepository userRepository,
                           AuthenticationManager authenticationManager) {
        this.manager = manager;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public boolean login(String userName, String password) {
        log.debug("Attempting login for user: {}", userName);
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userName, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Login successful for user: {}", userName);
            return true;
        } catch (Exception e) {
            log.error("Login failed for user: {} - Error: {}", userName, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        log.debug("Attempting to register user: {}", register.getUsername());
        
        if (manager.userExists(register.getUsername())) {
            log.warn("User already exists: {}", register.getUsername());
            return false;
        }

        UserEntity user = new UserEntity();
        user.setEmail(register.getUsername());
        String encodedPassword = encoder.encode(register.getPassword());
        log.debug("Encoded password for user {}: {}", register.getUsername(), encodedPassword);
        user.setPassword(encodedPassword);
        user.setFirstName(register.getFirstName());
        user.setLastName(register.getLastName());
        user.setPhoneNumber(register.getPhone());
        user.setRole(register.getRole());
        user.setEnabled(true);

        userRepository.save(user);
        log.debug("User registered successfully: {}", register.getUsername());
        return true;
    }

}

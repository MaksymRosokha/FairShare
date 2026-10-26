package com.rosokha.fairshare.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(User user) {
        log.info("Attempting to save user with email: {}", user.getEmail());
        userRepository.save(user);
        log.info("User successfully saved with email: {}", user.getEmail());
    }

    public boolean isUserAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);
        log.debug("Checking authentication status. Is authenticated: {}", isAuthenticated);
        return isAuthenticated;
    }

    @Transactional(readOnly = true)
    public User getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.debug("Fetching current authenticated user by email: {}", email);
        return userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(
                        () -> {
                            log.warn("Failed to find user with email: {} in the database", email);
                            return new IllegalArgumentException("User with email = " + email + " not found");
                        }
                );
    }
}

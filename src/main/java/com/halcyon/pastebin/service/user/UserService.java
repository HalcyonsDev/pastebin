package com.halcyon.pastebin.service.user;

import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.IUserRepository;
import com.halcyon.pastebin.security.JwtAuthentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this id not found."));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with this email not found."));
    }

    public User findCurrentUser() {
        JwtAuthentication jwtAuth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        return findByEmail(jwtAuth.getEmail());
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User updateName(String name) {
        User currentUser = findCurrentUser();
        currentUser.setName(name);

        userRepository.updateNameById(name, currentUser.getId());
        return currentUser;
    }
}

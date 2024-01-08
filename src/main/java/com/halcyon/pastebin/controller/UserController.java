package com.halcyon.pastebin.controller;

import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.service.user.UserService;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<User> getById(@PathVariable Long userId) {
        User user = userService.findById(userId);
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/update-name")
    public ResponseEntity<User> updateName(
            @RequestBody
            @Size(min = 2, max = 50, message = "Name must be more than 1 character and less than 50 characters.") String value
    ) {
        User user = userService.updateName(value);
        return ResponseEntity.ok(user);
    }
}

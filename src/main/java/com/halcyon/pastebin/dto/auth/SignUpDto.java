package com.halcyon.pastebin.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    @Email
    private String email;

    @Size(min = 2, max = 50, message = "Name must be more than 1 character and less than 50 characters.")
    private String name;

    @Size(min = 5, message = "Password must be more than 4 characters")
    private String password;
}

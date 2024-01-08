package com.halcyon.pastebin.dto.text;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewTextDto {
    @Size(min = 2, max = 100, message = "Content must be more than 1 character and less than 100 characters.")
    private String content;

    private Instant expirationTime;
}

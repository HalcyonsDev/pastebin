package com.halcyon.pastebin.dto.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewTextDto {
    private String value;
    private Instant expirationTime;
}

package com.halcyon.pastebin.controller;

import com.halcyon.pastebin.dto.text.NewTextDto;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.service.text.TextService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/texts")
@RequiredArgsConstructor
public class TextController {
    private final TextService textService;

    @PostMapping
    public ResponseEntity<Text> create(@RequestBody @Valid NewTextDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Text text = textService.create(dto);
        return ResponseEntity.ok(text);
    }

    @GetMapping("/{textId}")
    public ResponseEntity<Text> getById(@PathVariable Long textId) {
        Text text = textService.findById(textId);
        return ResponseEntity.ok(text);
    }
}

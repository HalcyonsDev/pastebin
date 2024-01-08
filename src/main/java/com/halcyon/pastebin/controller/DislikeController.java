package com.halcyon.pastebin.controller;

import com.halcyon.pastebin.dto.dislike.NewDislikeDto;
import com.halcyon.pastebin.model.Dislike;
import com.halcyon.pastebin.service.dislike.DislikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/dislikes")
@RequiredArgsConstructor
public class DislikeController {
    private final DislikeService dislikeService;

    @PostMapping
    public ResponseEntity<Dislike> create(@RequestBody @Valid NewDislikeDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Dislike dislike = dislikeService.create(dto);
        return ResponseEntity.ok(dislike);
    }

    @DeleteMapping("/{dislikeId}/delete")
    public ResponseEntity<String> delete(@PathVariable Long dislikeId) {
        String response = dislikeService.deleteById(dislikeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dislikeId}")
    public ResponseEntity<Dislike> getById(@PathVariable Long dislikeId) {
        Dislike dislike = dislikeService.findById(dislikeId);
        return ResponseEntity.ok(dislike);
    }


    @GetMapping("/text/{textId}")
    public ResponseEntity<List<Dislike>> getAllByTextId(@PathVariable Long textId) {
        List<Dislike> dislikes = dislikeService.findAllByTextId(textId);
        return ResponseEntity.ok(dislikes);
    }
}

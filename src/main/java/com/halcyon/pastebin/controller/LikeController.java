package com.halcyon.pastebin.controller;

import com.halcyon.pastebin.dto.like.NewLikeDto;
import com.halcyon.pastebin.model.Like;
import com.halcyon.pastebin.service.like.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<Like> create(@RequestBody @Valid NewLikeDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Like like = likeService.create(dto);
        return ResponseEntity.ok(like);
    }

    @DeleteMapping("/{likeId}/delete")
    public ResponseEntity<String> delete(@PathVariable Long likeId) {
        String response = likeService.deleteById(likeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<Like> getById(@PathVariable Long likeId) {
        Like like = likeService.findById(likeId);
        return ResponseEntity.ok(like);
    }

    @GetMapping("/text/{textId}")
    public ResponseEntity<List<Like>> getAllByTextId(@PathVariable Long textId) {
        List<Like> likes = likeService.findAllByTextId(textId);
        return ResponseEntity.ok(likes);
    }
}

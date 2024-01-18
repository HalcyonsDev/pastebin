package com.halcyon.pastebin.service.text;

import com.halcyon.pastebin.controller.TextController;
import com.halcyon.pastebin.dto.text.NewTextDto;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.ITextRepository;
import com.halcyon.pastebin.service.auth.AuthService;
import com.halcyon.pastebin.service.user.UserService;
import com.halcyon.pastebin.util.HashGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TextService {
    private final ITextRepository textRepository;
    private final UserService userService;
    private final AuthService authService;

    private final ApplicationContext applicationContext;

    public Text create(NewTextDto dto) {
        User creator = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = Text.builder()
                .content(dto.getContent())
                .expirationTime(dto.getExpirationTime())
                .creator(creator)
                .viewCount(0L)
                .build();

        Text savedText = textRepository.save(text);
        savedText.setHash(HashGenerator.generateShortURL(savedText.getId()));

        return textRepository.save(savedText);
    }

    public Text findById(Long id) {
        return textRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Text with this id not found."));
    }

    @Cacheable(value = "text", unless = "#text == null")
    public Text findByHash(String hash) {
        User viewer = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = textRepository.findByHash(hash)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Text with this hash not found."));

        if (text.getViewers().contains(viewer)) {
            return text;
        } else {
            Long viewCount = text.getViewCount() + 1;

            text.setViewCount(viewCount);
            text.getViewers().add(viewer);

            if (viewCount > 1) {
                getStringProxy().addToCache(text);
            }

            return textRepository.save(text);
        }
    }

    @CachePut(value = "text", key = "#text.hash")
    public void addToCache(Text text) {}
    
    private TextService getStringProxy() {
        return applicationContext.getBean(TextService.class);
    }
}

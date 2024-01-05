package com.halcyon.pastebin.service.text;

import com.halcyon.pastebin.dto.text.NewTextDto;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.ITextRepository;
import com.halcyon.pastebin.service.auth.AuthService;
import com.halcyon.pastebin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TextService {
    private final ITextRepository textRepository;
    private final UserService userService;
    private final AuthService authService;

    public Text create(NewTextDto dto) {
        User creator = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = new Text(dto.getValue(), dto.getExpirationTime(), creator);

        return textRepository.save(text);
    }

    public Text findById(Long id) {
        return textRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Text with this id not found."));
    }
}

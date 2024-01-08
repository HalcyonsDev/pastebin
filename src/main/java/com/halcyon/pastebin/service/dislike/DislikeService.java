package com.halcyon.pastebin.service.dislike;

import com.halcyon.pastebin.dto.dislike.NewDislikeDto;
import com.halcyon.pastebin.model.Dislike;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.IDislikeRepository;
import com.halcyon.pastebin.service.auth.AuthService;
import com.halcyon.pastebin.service.text.TextService;
import com.halcyon.pastebin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DislikeService {
    private final IDislikeRepository dislikeRepository;
    private final TextService textService;
    private final UserService userService;
    private final AuthService authService;

    public Dislike create(NewDislikeDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = textService.findById(dto.getTextId());

        if (dislikeRepository.existsByOwnerAndText(owner, text)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dislike for this text has already been created.");
        }

        Dislike dislike = new Dislike();
        dislike.setText(text);
        dislike.setOwner(owner);

        return dislikeRepository.save(dislike);
    }

    public String deleteById(Long dislikeId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Dislike dislike = findById(dislikeId);

        if (!dislike.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this dislike.");
        }

        dislikeRepository.delete(dislike);
        return "Dislike deleted successfully";
    }

    public Dislike findById(Long dislikeId) {
        return dislikeRepository.findById(dislikeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dislike with this id not found."));
    }

    public List<Dislike> findAllByTextId(Long textId) {
        return dislikeRepository.findAllByTextId(textId);
    }
}

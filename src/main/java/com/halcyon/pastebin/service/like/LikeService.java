package com.halcyon.pastebin.service.like;

import com.halcyon.pastebin.dto.like.NewLikeDto;
import com.halcyon.pastebin.model.Like;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.ILikeRepository;
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
public class LikeService {
    private final ILikeRepository likeRepository;
    private final TextService textService;
    private final UserService userService;
    private final AuthService authService;

    public Like create(NewLikeDto dto) {
        User owner = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = textService.findById(dto.getTextId());

        if (likeRepository.existsByOwnerAndText(owner, text)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Like for this text has already been created.");
        }

        Like like = new Like();
        like.setText(text);
        like.setOwner(owner);

        return likeRepository.save(like);
    }

    public String deleteById(Long likeId) {
        User user = userService.findByEmail(authService.getAuthInfo().getEmail());
        Like like = findById(likeId);

        if (!like.getOwner().equals(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this like.");
        }

        likeRepository.delete(like);
        return "Like deleted successfully.";
    }

    public Like findById(Long likeId) {
        return likeRepository.findById(likeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Like with this id not found."));
    }

    public List<Like> findAllByTextId(Long textId) {
        return likeRepository.findAllByTextId(textId);
    }
}

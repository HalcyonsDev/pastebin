package com.halcyon.pastebin.service.comment;

import com.halcyon.pastebin.dto.comment.NewCommentDto;
import com.halcyon.pastebin.model.Comment;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import com.halcyon.pastebin.repository.ICommentRepository;
import com.halcyon.pastebin.service.auth.AuthService;
import com.halcyon.pastebin.service.text.TextService;
import com.halcyon.pastebin.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ICommentRepository commentRepository;
    private final UserService userService;
    private final AuthService authService;
    private final TextService textService;

    public Comment create(NewCommentDto dto) {
        User creator = userService.findByEmail(authService.getAuthInfo().getEmail());
        Text text = textService.findById(dto.getTextId());

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .text(text)
                .creator(creator)
                .build();

        return commentRepository.save(comment);
    }
}

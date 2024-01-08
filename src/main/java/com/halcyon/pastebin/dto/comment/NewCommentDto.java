package com.halcyon.pastebin.dto.comment;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCommentDto {
    private Long textId;

    @Size(min = 2, max = 100, message = "Content must be more than 1 character and less than 100 characters.")
    private String content;
}

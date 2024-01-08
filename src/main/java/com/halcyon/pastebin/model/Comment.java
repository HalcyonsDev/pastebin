package com.halcyon.pastebin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @JsonManagedReference
    private User creator;

    @ManyToOne
    @JoinColumn(name = "text_id", referencedColumnName = "id")
    @JsonManagedReference
    private Text text;
}

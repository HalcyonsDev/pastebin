package com.halcyon.pastebin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Token> tokens;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<Text> createdTexts;

    @OneToMany(mappedBy = "creator")
    @JsonBackReference
    private List<Comment> comments;

    @ManyToMany(mappedBy = "viewers")
    @JsonBackReference
    private List<Text> viewedTexts;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Like> likes;

    @OneToMany(mappedBy = "owner")
    @JsonBackReference
    private List<Dislike> dislikes;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}

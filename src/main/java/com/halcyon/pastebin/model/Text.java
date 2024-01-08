package com.halcyon.pastebin.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "texts")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "expiration_time")
    private Instant expirationTime;

    @Column(name = "hash")
    private String hash;

    @Column(name = "view_count")
    private Long viewCount;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @JsonManagedReference
    private User creator;

    @ManyToMany
    @JoinTable(
            name = "texts_viewers",
            joinColumns = @JoinColumn(name = "text_id"),
            inverseJoinColumns = @JoinColumn(name = "viewer_id")
    )
    @JsonManagedReference
    private List<User> viewers;

    @OneToMany(mappedBy = "text")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "text")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Dislike> dislikes;

    @OneToMany(mappedBy = "text")
    @JsonBackReference
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<Comment> comments;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}

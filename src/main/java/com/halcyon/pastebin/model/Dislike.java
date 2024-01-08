package com.halcyon.pastebin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "dislikes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dislike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_at")
    private Instant createdAt;

    @ManyToOne
    @JoinColumn(name = "text_id", referencedColumnName = "id")
    @JsonManagedReference
    private Text text;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @JsonManagedReference
    private User owner;

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}

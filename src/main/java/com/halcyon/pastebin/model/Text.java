package com.halcyon.pastebin.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "texts")
@Getter
@Setter
@NoArgsConstructor
public class Text {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "expiration_time")
    private Instant expirationTime;

    @ManyToOne
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    @JsonManagedReference
    private User creator;

    public Text(String value, Instant expirationTime, User creator) {
        this.value = value;
        this.expirationTime = expirationTime;
        this.creator = creator;
    }

    @PrePersist
    private void onCreate() {
        createdAt = Instant.now();
    }
}

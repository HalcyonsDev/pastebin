package com.halcyon.pastebin.repository;

import com.halcyon.pastebin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User user SET user.name = ?1 WHERE user.id = ?2")
    void updateNameById(String name, Long id);
}

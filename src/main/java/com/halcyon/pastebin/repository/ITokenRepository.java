package com.halcyon.pastebin.repository;

import com.halcyon.pastebin.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ITokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByValue(String value);
}

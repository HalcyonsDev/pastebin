package com.halcyon.pastebin.repository;

import com.halcyon.pastebin.model.Text;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface ITextRepository extends JpaRepository<Text, Long> {
    Optional<Text> findByHash(String hash);

    @Transactional
    @Modifying
    @Query("DELETE FROM Text text WHERE text.expirationTime <= :currentTime")
    Text deleteByExpirationTime(@Param("currentTime") Instant currentTime);
}

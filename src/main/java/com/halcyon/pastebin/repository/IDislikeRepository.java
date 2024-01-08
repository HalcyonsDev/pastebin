package com.halcyon.pastebin.repository;

import com.halcyon.pastebin.model.Dislike;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IDislikeRepository extends JpaRepository<Dislike, Long> {
    List<Dislike> findAllByTextId(Long textId);
    boolean existsByOwnerAndText(User owner, Text text);
}

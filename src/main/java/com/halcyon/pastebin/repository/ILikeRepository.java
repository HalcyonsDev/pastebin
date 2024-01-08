package com.halcyon.pastebin.repository;

import com.halcyon.pastebin.model.Like;
import com.halcyon.pastebin.model.Text;
import com.halcyon.pastebin.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByTextId(Long textId);
    boolean existsByOwnerAndText(User owner, Text text);
}

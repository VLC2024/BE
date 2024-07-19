package com.vlc.maeummal.domain.word.repository;

import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WordSetRepository extends JpaRepository<WordSetEntity, Long> {
    // word set 저장

    WordSetEntity save(WordSetEntity wordSet);
    // word set 가져오기
    Optional<WordSetEntity> findById(Long id);
}
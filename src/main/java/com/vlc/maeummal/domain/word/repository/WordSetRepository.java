
package com.vlc.maeummal.domain.word.repository;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WordSetRepository extends JpaRepository<WordSetEntity, Long> {
    // word set 저장

    WordSetEntity save(WordSetEntity wordSet);
    // word set 가져오기
    Optional<WordSetEntity> findById(Long id);
    // all wordset 가져오기

    List<WordSetEntity> findByTitleContaining(String title);
    List<WordSetEntity> findByTitle(String title);

    @Override
    List<WordSetEntity> findAll();

    List<WordSetEntity> findByCreater(MemberEntity member);
}


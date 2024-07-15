package com.vlc.maeummal.domain.word.repository;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WordRepository extends JpaRepository<WordEntity, Long> {
    // word 저장
    WordEntity save(WordEntity word);

    // word 가져오기
    Optional<WordEntity> findById(Long id);

    // set id로 word list 가져오기

    /*
    * 다시 확인 해봐야 함.
    * */
    List<WordEntity> findByWordSetId(Long setId);

    // word 삭제

    // word 수정

    //

    // wordSet저장


    //

}

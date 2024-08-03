package com.vlc.maeummal.domain.template.template5.repository;

import com.vlc.maeummal.domain.template.template5.entity.WordListEntity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordListRepository extends JpaRepository<WordListEntity, Long> {
    WordListEntity save(WordListEntity wordList);
}

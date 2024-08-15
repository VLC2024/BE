package com.vlc.maeummal.domain.template.template5.repository;

import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface WordCardRepository extends JpaRepository<WordCardEntity, Long> {
//    WordCardEntity save(WordCardEntity wordCard);
}

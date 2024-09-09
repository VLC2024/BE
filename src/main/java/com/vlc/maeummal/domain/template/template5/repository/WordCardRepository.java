package com.vlc.maeummal.domain.template.template5.repository;

import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordCardRepository extends JpaRepository<WordCardEntity, Long> {
//    WordCardEntity save(WordCardEntity wordCard);
//List<Long> findTemplate5IdByWordsetId(Long wordSetId);
    @Query("SELECT wc.temp5.id FROM WordCardEntity wc WHERE wc.wordsetId = :wordSetId")
    List<Long> findTemplate5IdByWordsetId(@Param("wordSetId") Long wordSetId);
}

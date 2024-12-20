package com.vlc.maeummal.domain.template.template1.repository;

import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface Template1Repository extends JpaRepository<Template1Entity, Long>{

    Optional<Template1Entity> findById(Long id);

    List<Template1Entity> findByCreaterId(Long createrId);

    // 낱말 카드가 포함된 템플릿들을 찾는 쿼리 메서드
    List<Template1Entity> findByWordEntitiesContaining(WordEntity word);
}

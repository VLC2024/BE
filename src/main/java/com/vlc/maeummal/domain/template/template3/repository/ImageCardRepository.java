package com.vlc.maeummal.domain.template.template3.repository;

import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageCardRepository extends JpaRepository<ImageCardEntity, Long> {

    ImageCardEntity save(ImageCardEntity imageCard);

    Optional<ImageCardEntity> findById(Long id);

    List<ImageCardEntity> findByTemplate3Id(Long id);
}

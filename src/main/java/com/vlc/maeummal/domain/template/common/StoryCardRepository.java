package com.vlc.maeummal.domain.template.common;

import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface StoryCardRepository extends JpaRepository<StoryCardEntity, Long> {

    /*StoryCardEntity save(StoryCardEntity storyCard);

    Optional<StoryCardEntity> findById(Long id);*/

    List<StoryCardEntity> findByTemplate2Id(Long id);

}

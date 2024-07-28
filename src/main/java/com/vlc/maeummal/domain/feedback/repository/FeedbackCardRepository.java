package com.vlc.maeummal.domain.feedback.repository;

import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackCardRepository extends JpaRepository<FeedbackCardEntity, Long> {
    FeedbackCardEntity save(FeedbackCardEntity feedbackCard);

    List<FeedbackCardEntity> findAllBy();
}

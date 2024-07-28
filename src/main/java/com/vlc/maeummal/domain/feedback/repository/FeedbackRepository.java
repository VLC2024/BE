package com.vlc.maeummal.domain.feedback.repository;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    FeedbackEntity save(FeedbackEntity feedback);
    List<FeedbackEntity> findAllBy();

}
/**
 *
 * JpaRepository<Template3Entity, Long> {
 *     Template3Entity save(Template3Entity template3);
 *     Optional<Template3Entity> findById(Long id);
 *     @Override
 *     List<Template3Entity> findAll();
 * }
 *
 * */
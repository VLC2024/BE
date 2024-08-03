package com.vlc.maeummal.domain.feedback.repository;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    FeedbackEntity save(FeedbackEntity feedback);
    List<FeedbackEntity> findAllBy();

    Optional<FeedbackEntity> findById(Long id);

    List<FeedbackEntity> findAllByStudent(MemberEntity student);

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
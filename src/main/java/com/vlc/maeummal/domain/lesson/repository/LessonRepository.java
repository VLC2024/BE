package com.vlc.maeummal.domain.lesson.repository;

import com.vlc.maeummal.domain.lesson.entity.LessonEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<LessonEntity, Long> {

    Optional<LessonEntity> findByLessonId(Long id);
}


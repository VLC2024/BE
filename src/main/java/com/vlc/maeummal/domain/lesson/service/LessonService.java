package com.vlc.maeummal.domain.lesson.service;

import com.vlc.maeummal.domain.lesson.dto.LessonDTO;
import com.vlc.maeummal.domain.lesson.entity.LessonEntity;
import com.vlc.maeummal.domain.lesson.repository.LessonRepository;
import com.vlc.maeummal.global.enums.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    // Lesson 생성 후 저장
    public LessonDTO createLesson(LessonDTO lessonDTO) {
        LessonEntity lessonEntity = mapToEntity(lessonDTO);
        LessonEntity savedEntity = lessonRepository.save(lessonEntity);
        return mapToDTO(savedEntity);
    }

    private LessonEntity mapToEntity(LessonDTO lessonDTO) {
        return LessonEntity.builder()
                .title(lessonDTO.getTitle())
                .content(lessonDTO.getContent())
                .created_at(LocalDateTime.now())
                .view(0)
                .category(Category.valueOf(lessonDTO.getCategory()))
                .difficulty(Integer.parseInt(lessonDTO.getDifficulty()))
                .build();
    }

    private LessonDTO mapToDTO(LessonEntity lessonEntity) {
        return LessonDTO.builder()
                .leesonId(lessonEntity.getLessonId())
                .title(lessonEntity.getTitle())
                .content(lessonEntity.getContent())
                .created_at(lessonEntity.getCreated_at().toString())
                .view(lessonEntity.getView().toString())
                .category(lessonEntity.getCategory().name())
                .difficulty(lessonEntity.getDifficulty().toString())
                .build();
    }
}


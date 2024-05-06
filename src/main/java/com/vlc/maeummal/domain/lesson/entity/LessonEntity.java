package com.vlc.maeummal.domain.lesson.entity;

import com.vlc.maeummal.global.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lesson")
public class LessonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @CreatedDate
    private LocalDateTime created_at; //created_at 레슨 등록 날짜/시간

    @Column(nullable = false)
    private Integer view;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer difficulty;

    @Column
    private String field;

    @OneToMany(mappedBy = "lesson")
    private List<WordEntity> words;
}

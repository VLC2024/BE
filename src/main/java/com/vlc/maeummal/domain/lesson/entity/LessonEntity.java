
package com.vlc.maeummal.domain.lesson.entity;

import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.vlc.maeummal.domain.lesson.entity.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "lesson")
public class LessonEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Column(nullable = false)
    private String title;

    @Column
    private String content;

    @Column(nullable = false)
    private Integer view;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private Integer difficulty;

    @Column(nullable = false)
    private String creator;

    @OneToMany(mappedBy = "lessonEntity", cascade = CascadeType.ALL)
    private List<WordEntity> words = new ArrayList<>();
}


package com.vlc.maeummal.domain.lesson.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

//    @Column(nullable = true)
//    private String content;

    @Column(nullable = true)
    private Integer view;

    @Column(nullable = false)
    private Integer level;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity creator;

    @Column(nullable = false)
    private Long templateId;



}

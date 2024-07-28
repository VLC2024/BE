
package com.vlc.maeummal.domain.lesson.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.Category;
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

    @Column(nullable = true)
    private String content;

    @Column(nullable = true)
    private Integer view;

//    @Enumerated(EnumType.STRING)
//    private Category category;

    @Column(nullable = false)
    private Integer level;

    @Column(nullable = false)
    @ManyToOne
    private MemberEntity creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity member;




//    @OneToMany(mappedBy = "lessonEntity", cascade = CascadeType.ALL)
//    private List<WordEntity> words = new ArrayList<>();
}

package com.vlc.maeummal.domain.feedback.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "feedback")
public class FeedbackEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private Long id;

    private Long templateId;

    private String aiFeedback;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private MemberEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private MemberEntity teacher;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackCardEntity> correctFeedbackCards = new ArrayList<>();

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackCardEntity> studentFeedbackCards = new ArrayList<>();

    public void setCorrectFeedbackCards(List<FeedbackCardEntity> correctFeedbackCards) {
        this.correctFeedbackCards = correctFeedbackCards;
        for (FeedbackCardEntity card : correctFeedbackCards) {
            card.setFeedback(this);
        }
    }

    public void setStudentFeedbackCards(List<FeedbackCardEntity> studentFeedbackCards) {
        this.studentFeedbackCards = studentFeedbackCards;
        for (FeedbackCardEntity card : studentFeedbackCards) {
            card.setFeedback(this);
        }
    }


}

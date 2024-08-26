package com.vlc.maeummal.domain.feedback.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.CardType;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String aiFeedback;

    private String title;

    private String solution; // 선생님이 작성한 템플릿에 대한 해설

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private MemberEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = true)
    private MemberEntity teacher;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FeedbackCardEntity> feedbackCards = new ArrayList<>();

    public List<FeedbackCardEntity> getCorrectFeedbackCards() {
        return feedbackCards.stream()
                .filter(card -> card.getCardType() == CardType.CORRECT)
                .collect(Collectors.toList());
    }


    public List<FeedbackCardEntity> getStudentFeedbackCards() {
        return feedbackCards.stream()
                .filter(card -> card.getCardType() == CardType.STUDENT)
                .collect(Collectors.toList());
    }
    // 카드를 설정하는 메서드
    public void setCorrectFeedbackCards(List<FeedbackCardEntity> correctFeedbackCards) {
        if (this.feedbackCards == null) {
            this.feedbackCards = new ArrayList<>();
        }
        for (FeedbackCardEntity card : correctFeedbackCards) {
            card.setFeedback(this);
        }

        correctFeedbackCards.forEach(card -> card.setCardType(CardType.CORRECT));
        this.feedbackCards.addAll(correctFeedbackCards);
    }

    public void setStudentFeedbackCards(List<FeedbackCardEntity> studentFeedbackCards) {
        if (this.feedbackCards == null) {
            this.feedbackCards = new ArrayList<>();
        }
        for (FeedbackCardEntity card : studentFeedbackCards) {
            card.setFeedback(this);
        }
        studentFeedbackCards.forEach(card -> card.setCardType(CardType.STUDENT));
        this.feedbackCards.addAll(studentFeedbackCards);
    }



}

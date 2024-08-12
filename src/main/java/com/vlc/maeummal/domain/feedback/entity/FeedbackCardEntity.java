package com.vlc.maeummal.domain.feedback.entity;

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
@Table(name = "feedbackCard")
public class FeedbackCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackCard_id")
    private Long id;
    @Column(nullable = true)
    private String image;
   // 아래 두 값은 템플릿 별로 사용여부 결정.
    // 배열 기반 템플릿 (2,4) -> 아래 필드 사용 x
    // 매칭 기반 템플릿 (1, 3, 5) -> 아래 필드 사용 0
    @Column(nullable=true)
    private String adjective;
    @Column(nullable=true)
    private String noun;

    @Column
    private String meaning;
    @Column
    private String description;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feedbackId")
    private FeedbackEntity feedback;

//    public void setFeedback(FeedbackEntity feedback) {
//        this.feedback = feedback;
//    }

}
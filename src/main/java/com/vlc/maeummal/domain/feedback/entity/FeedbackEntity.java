package com.vlc.maeummal.domain.feedback.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.common.Template;
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
    @JoinColumn(name = "memberId")
    private MemberEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private MemberEntity teacher;

    @Enumerated
    private Template templateType;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "feedbackCard", cascade = CascadeType.ALL, orphanRemoval = true)
    List<FeedbackCardEntity> feedbackCardEntityList = new ArrayList<>();





}

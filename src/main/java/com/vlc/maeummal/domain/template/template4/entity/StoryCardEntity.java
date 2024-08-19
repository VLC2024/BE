package com.vlc.maeummal.domain.template.template4.entity;

import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
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
@Table(name = "StoryCard")
public class StoryCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storyCard_id")
    private Long id;

    @Column(nullable = true)
    private String image;

    @Column(nullable=true)
    private Integer answerNumber; // 정답 순번

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Integer cardCount; // 카드 갯수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template4Id")
    private Template4Entity template4;
}
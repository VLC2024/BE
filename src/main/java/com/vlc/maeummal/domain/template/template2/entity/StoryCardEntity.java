package com.vlc.maeummal.domain.template.template2.entity;

import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
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
    private Integer answerNumber;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Integer cardCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template2Id")
    private Template2Entity template2;


}

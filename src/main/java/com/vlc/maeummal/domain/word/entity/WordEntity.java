package com.vlc.maeummal.domain.word.entity;

import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
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
@Table(name = "word")
public class WordEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column(nullable = true)
    private String meaning;
    @Column(nullable = true)
    private String image;
    @Column(nullable = true)
    private String prompt;
    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordSetId")
    private WordSetEntity wordSet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp5_id")
    private Template5Entity temp5;

    @ManyToOne
    @JoinColumn(name = "temp1_id")
    private Template1Entity template1Entity;
}

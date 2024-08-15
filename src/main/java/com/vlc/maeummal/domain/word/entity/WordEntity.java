package com.vlc.maeummal.domain.word.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.global.common.BaseEntity;
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
    @JsonIgnore
    private WordSetEntity wordSet;

    /*@OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true)
    List<WordCardEntity> wordListEntities = new ArrayList<>();*/

    @ManyToOne
    @JoinColumn(name = "temp1_id")
    private Template1Entity template1Entity;
}

package com.vlc.maeummal.domain.word.entity;

import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.Category;
import com.vlc.maeummal.global.enums.Gender;
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
@Table(name="wordSet")
public class WordSetEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wordSet_id")
    private Long id;
    @Column(nullable=true)
    private String title;
    @Column(nullable=true)
    private String description;
    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "wordSet", cascade = CascadeType.ALL, orphanRemoval = true)
    List <WordEntity> wordEntities = new ArrayList<>();


}

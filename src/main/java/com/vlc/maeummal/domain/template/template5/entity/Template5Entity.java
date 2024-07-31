package com.vlc.maeummal.domain.template.template5.entity;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.Category;
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
@Table(name = "temp5")
public class Template5Entity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp5_id")
    private Long id;


    @OneToMany(mappedBy = "temp5", cascade = CascadeType.ALL, orphanRemoval = true)
    List <WordEntity> wordEntities = new ArrayList<>();

}

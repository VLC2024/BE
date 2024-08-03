package com.vlc.maeummal.domain.template.template1.entity;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
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
@Table(name = "temp1")
public class Template1Entity {

    @Id
    @Column(name = "temp1_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Builder.Default
    @OneToMany(mappedBy = "template1Entity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordEntity> wordEntities = new ArrayList<>();

}
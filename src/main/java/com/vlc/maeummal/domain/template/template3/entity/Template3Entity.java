package com.vlc.maeummal.domain.template.template3.entity;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
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
@Table(name = "template3")
public class Template3Entity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template3_id")
    private Long id;


    @Column(nullable = true)
    private String description;
//    @Column(nullable=true)
//    private String hint;
    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "template3", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ImageCardEntity> imageCardEntityList = new ArrayList<>();



}

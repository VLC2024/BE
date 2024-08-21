package com.vlc.maeummal.domain.template.template1.entity;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.enums.TemplateType;
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
public class Template1Entity extends TemplateEntity {

    @Id
    @Column(name = "temp1_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @OneToMany(mappedBy = "template1Entity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WordEntity> wordEntities = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    TemplateType type;
    @Column(nullable = true)
    private Integer imageNum;
    @Column(nullable = true)
    private String description;
}

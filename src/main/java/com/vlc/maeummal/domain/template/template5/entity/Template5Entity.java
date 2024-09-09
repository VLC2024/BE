package com.vlc.maeummal.domain.template.template5.entity;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.global.common.BaseEntity;
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
@Table(name = "temp5")
public class Template5Entity extends TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "temp5_id")
    private Long id;

    @Column
    private TemplateType type;
    @Column(nullable = true)
    private String title;

    @Column
    private Integer imageNum;

    @Column
    private String description;

    @OneToMany(mappedBy = "temp5", cascade = CascadeType.ALL, orphanRemoval = true)
    List <WordCardEntity> wordListEntities = new ArrayList<>();

}

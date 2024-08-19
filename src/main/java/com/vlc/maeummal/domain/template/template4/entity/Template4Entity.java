package com.vlc.maeummal.domain.template.template4.entity;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.template2.entity.StoryCardEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "template4")
public class Template4Entity extends TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template4_id")
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String hint;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "template4", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StoryCardEntity> storyCardEntityList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TemplateType type;

}

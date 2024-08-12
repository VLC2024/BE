package com.vlc.maeummal.domain.template.template2.entity;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.global.converter.StringListConverter;
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
@Table(name = "template2")
public class Template2Entity extends TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template2_id")
    private Long id;

    @Column(nullable = true)
    private String title;

    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private String hint;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "template2", cascade = CascadeType.ALL, orphanRemoval = true)
    List<StoryCardEntity> storyCardEntityList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TemplateType type;



}
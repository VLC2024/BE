package com.vlc.maeummal.domain.template.template3.entity;

//import com.vlc.maeummal.domain.template.common.Template;
import com.vlc.maeummal.domain.template.common.entity.TemplateEntity;
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
@Table(name = "template3")
public class Template3Entity extends TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "template3_id")
    private Long id;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private Integer imageNum;

    @OneToMany(mappedBy = "template3", cascade = CascadeType.ALL, orphanRemoval = true)
    List<ImageCardEntity> imageCardEntityList = new ArrayList<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TemplateType type;

    @Convert(converter = StringListConverter.class)
    @Column(name = "options")
    private List<String> optionList = new ArrayList<>();



}

package com.vlc.maeummal.domain.template.template3.entity;

//import com.vlc.maeummal.domain.template.common.Template;
import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.global.common.TemplateType;
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
public class Template3Entity extends TemplateEntity {
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

//    @PostLoad
//    private void setTemplateType() {
//        // 엔티티가 로드된 후에 타입을 설정합니다.
//        this.setType(TemplateType.TEMPLATE3);
//    }
    @Enumerated(EnumType.STRING)
    TemplateType type;

}

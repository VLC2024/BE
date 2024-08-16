package com.vlc.maeummal.domain.template.common;

import com.vlc.maeummal.domain.lesson.entity.LessonEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class TemplateEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Enumerated(EnumType.STRING)
    TemplateType type;

    String title;

//    @OneToOne(mappedBy = "template")
//    private LessonEntity lesson;
    @Column(nullable = true)
    Integer level;

    public abstract Long getId();
    public abstract TemplateType getType();
    public abstract Integer getImageNum();
    public abstract String getDescription();

}

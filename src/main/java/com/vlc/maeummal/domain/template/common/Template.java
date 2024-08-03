//package com.vlc.maeummal.domain.template.common;
//
//import com.vlc.maeummal.global.common.TemplateType;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedDate;
//import org.springframework.data.annotation.LastModifiedDate;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.time.LocalDateTime;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@Getter
//@Setter
//public abstract class Template {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//
//    @Enumerated(EnumType.STRING)
//    TemplateType type;
//
//    @CreatedDate
//    private LocalDateTime createdAt;
//
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
//}

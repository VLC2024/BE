package com.vlc.maeummal.domain.template.common.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// Badge 엔티티
@Entity
@Getter
@Setter
public class BadgeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TemplateType templateType;

    @Column(nullable = false)
    private Long userId;

    // 배지를 수여받은 사용자
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private MemberEntity user;

}

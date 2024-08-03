package com.vlc.maeummal.domain.member.entity;

import com.vlc.maeummal.global.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="teacher")
public class TeacherEntity extends MemberEntity{
    @Column(nullable=true)
    private String organization;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Role role = Role.T;
}

package com.vlc.maeummal.domain.member.entity;

import com.vlc.maeummal.global.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="student")
public class StudentEntity extends MemberEntity{

    @Column(nullable=true)
    private String pinCode;

    @Column(nullable=true)
    private Integer iQ;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.S;
}

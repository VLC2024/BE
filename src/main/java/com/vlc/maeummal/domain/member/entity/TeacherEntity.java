package com.vlc.maeummal.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="teacher")
public class TeacherEntity extends MemberEntity{
    @Column(nullable=true)
    private String organization;
}

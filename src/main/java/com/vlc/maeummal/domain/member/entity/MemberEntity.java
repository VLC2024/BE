package com.vlc.maeummal.domain.member.entity;
import com.vlc.maeummal.domain.lesson.entity.LessonEntity;

import com.vlc.maeummal.global.enums.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable=true)
    private String nickname;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column(nullable=true)
    private String name;

    @Column(nullable=true)
    private String phoneNumber;

    @Column(nullable=true)
    private String image;

    @Column(nullable=true)
    private String age;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = true, unique = true)
    private Long PinCode;

    // 매칭된 선생님의 ID를 저장할 필드
    @Column(nullable = true)
    private Long teacherId;

}
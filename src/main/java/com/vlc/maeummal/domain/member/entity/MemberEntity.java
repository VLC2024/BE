package com.vlc.maeummal.domain.member.entity;


import com.vlc.maeummal.global.enums.Gender;
import jakarta.persistence.*;

@MappedSuperclass
@Table(name="member")
public abstract class MemberEntity {

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

    @Column(nullable = true)
    private Long PinCode;
}
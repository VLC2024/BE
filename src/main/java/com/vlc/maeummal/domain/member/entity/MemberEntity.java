package com.vlc.maeummal.domain.member.entity;


//import com.vlc.maeummal.domain.myPage.entity.MatchingEntity;
import com.vlc.maeummal.global.emums.Gender;
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

    @Column(nullable=false)
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

    @Column(nullable = false, unique = true)
    private Long PinCode;






}
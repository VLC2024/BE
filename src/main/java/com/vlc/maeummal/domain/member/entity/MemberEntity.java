package com.vlc.maeummal.domain.member.entity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
//import com.vlc.maeummal.domain.lesson.entity.LessonEntity;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Builder
@Setter
@Getter
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


    @Column(nullable=false, unique = true)
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

//    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, orphanRemoval = true)
//    List <LessonEntity> lessonEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEntity> feedbackEntityListForStudent = new ArrayList<>();

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEntity> feedbackEntityListForTeacher = new ArrayList<>();

}
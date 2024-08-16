package com.vlc.maeummal.domain.member.entity;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.Gender;
import com.vlc.maeummal.global.enums.Iq;
import com.vlc.maeummal.global.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.List;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="member")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable=false, unique = true)
    private String email;

    @Column(nullable=false)
    private String password;

    @Column
    private String name;

    @Column
    private String phoneNumber;

    @Column
    private String image;

    @Column
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    @Column
    private Gender gender;

    @Column(nullable=true, unique = true)
    private String pinCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable=true)
    private Iq iq;

    @Column
    private Integer score; // 뱃지 획득용 총 점수

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEntity> feedbackEntityListForStudent;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private MemberEntity teacher;

    @Column(nullable=true)
    private String organization;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedbackEntity> feedbackEntityListForTeacher;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberEntity> matchingStudents;

    // PIN 코드 생성자
    @PrePersist
    private void generatePinCode() {
        if (this.pinCode == null) {
            this.pinCode = UUID.randomUUID().toString();
        }
    }
}
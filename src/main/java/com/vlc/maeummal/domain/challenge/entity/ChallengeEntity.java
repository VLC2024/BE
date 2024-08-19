package com.vlc.maeummal.domain.challenge.entity;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "challenge")
public class ChallengeEntity  extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id")
    private Long id;

    private boolean completed; // 미션 완료 상태

    LocalDateTime date;

    String title = "오늘의 챌린지";

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member; // 연관된 사용자

    private boolean wordMission;
    private boolean templateMission;
    private boolean prepMission;


}

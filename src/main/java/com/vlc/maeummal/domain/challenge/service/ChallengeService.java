package com.vlc.maeummal.domain.challenge.service;
import com.vlc.maeummal.domain.challenge.dto.ChallengeResponseDTO;
import com.vlc.maeummal.domain.challenge.entity.ChallengeEntity;
import com.vlc.maeummal.domain.challenge.repository.ChallengeRepository;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.global.enums.MissionType;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ChallengeService {
    final MemberReposirotyUsingId memberRepository;
    final ChallengeRepository challengeRepository;

    public  void completeMission(Long id, MissionType mission ) {
        ChallengeEntity challenge = challengeRepository.findByMember(memberRepository.findById(id).get());
        switch (mission) {
            case PREP -> {
                challenge.setPrepMission(true);
                            }
            case TEMP -> {
                challenge.setTemplateMission(true);
            }
            case WORD -> {
                challenge.setWordMission(true);
            }
        }
        if (challenge.isTemplateMission() && challenge.isPrepMission() &&
        challenge.isWordMission()) {
            challenge.setCompleted(true);
        }

    }
    public ChallengeResponseDTO.GetChallengeDTO getChallnege(Long studentId) {
        // id값 가져오기
        ChallengeEntity challenge = challengeRepository.findByMember(memberRepository.findById(studentId).get());
        return ChallengeResponseDTO.GetChallengeDTO.convertChallenge(challenge);


    }
    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 실행
    public void resetChallenges() {
        List<ChallengeEntity> challenges = challengeRepository.findAll();
        for (ChallengeEntity challenge : challenges) {
            challenge.setCompleted(false);
            challenge.setWordMission(false);
            challenge.setTemplateMission(false);
            challenge.setPrepMission(false);
            challenge.setDate(LocalDateTime.now()); // 날짜 업데이트
        }
        challengeRepository.saveAll(challenges); // 모든 챌린지 저장
    }



}

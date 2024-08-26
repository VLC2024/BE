package com.vlc.maeummal.domain.challenge.repository;

import com.vlc.maeummal.domain.challenge.entity.ChallengeEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long> {

    // MemberEntity의 ID로 ChallengeEntity를 찾는 메서드
    ChallengeEntity findByMember(MemberEntity member);

}

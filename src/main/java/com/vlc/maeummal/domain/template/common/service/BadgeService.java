package com.vlc.maeummal.domain.template.common.service;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import com.vlc.maeummal.domain.template.common.entity.BadgeEntity;
import com.vlc.maeummal.domain.template.common.repository.BadgeRepository;
import com.vlc.maeummal.global.enums.TemplateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository, MemberRepository memberRepository) {
        this.badgeRepository = badgeRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public BadgeEntity awardBadge(Long memberId, TemplateType templateType) {
        // 사용자 정보 조회
        MemberEntity member = memberRepository.findById(String.valueOf(memberId))
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 새로운 뱃지 엔티티 생성
        BadgeEntity badge = new BadgeEntity();
        badge.setTemplateType(templateType);
        badge.setUserId(memberId);

        // 뱃지 저장
        return badgeRepository.save(badge);
    }

    // 유저가 받은 모든 뱃지를 조회하는 메서드
    public List<BadgeEntity> getBadgesByUserId(Long userId) {
        return badgeRepository.findByUserId(userId);
    }
}

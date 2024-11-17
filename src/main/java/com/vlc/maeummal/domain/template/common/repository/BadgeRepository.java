package com.vlc.maeummal.domain.template.common.repository;

import com.vlc.maeummal.domain.template.common.entity.BadgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<BadgeEntity, Long> {
    List<BadgeEntity> findByUserId(Long userId);  // 특정 사용자가 받은 모든 뱃지를 조회
}
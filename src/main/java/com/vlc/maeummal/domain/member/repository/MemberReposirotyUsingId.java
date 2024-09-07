package com.vlc.maeummal.domain.member.repository;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberReposirotyUsingId extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findById(Long id);

    // 특정 학생의 ID로 해당 학생의 교사(teacher)를 찾는 메소드
    @Query("SELECT m.teacher FROM MemberEntity m WHERE m.memberId = :memberId")
    Optional<MemberEntity> findTeacherByMemberId(Long memberId);
}

package com.vlc.maeummal.domain.member.repository;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {

    MemberEntity findByEmail(String email);

    Boolean existsByEmail(String email);

    MemberEntity findByEmailAndPassword(String email, String password);

    Optional<MemberEntity> findByPinCode(String pinCode);
    // 메서드 이름을 findByTeacher_MemberId로 수정
    List<MemberEntity> findByTeacher_MemberId(Long teacherId);
}

package com.vlc.maeummal.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,String> {
    MemberEntity findByName(String name);

    MemberEntity findByEmail(String email);

    Boolean existsByEmail(String email);
    MemberEntity findByEmailAndPassword(String email, String password);
}

package com.vlc.maeummal.domain.member.repository;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberReposirotyUsingId extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findById(Long id);
}

package com.vlc.maeummal.domain.template.template4.repository;

import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Template4Repository extends JpaRepository<Template4Entity,Long> {
    Optional<Template4Entity> findById(Long temp4Id);
}

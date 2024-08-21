package com.vlc.maeummal.domain.template.template1.repository;

import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Template1Repository extends JpaRepository<Template1Entity, Integer>{

    Optional<Template1Entity> findById(Integer id);
}

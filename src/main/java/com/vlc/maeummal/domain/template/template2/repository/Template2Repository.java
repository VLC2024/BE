package com.vlc.maeummal.domain.template.template2.repository;

import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface Template2Repository extends JpaRepository<Template2Entity, Long> {
    Template2Entity save(Template2Entity template);
    Optional<Template2Entity> findById(Long id);
    @Override
    List<Template2Entity> findAll();
}
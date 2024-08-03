package com.vlc.maeummal.domain.template.template3.repository;

import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Template3Repository extends JpaRepository<Template3Entity, Long> {
    Template3Entity save(Template3Entity template3);
    Optional<Template3Entity> findById(Long id);
    @Override
    List<Template3Entity> findAll();
}

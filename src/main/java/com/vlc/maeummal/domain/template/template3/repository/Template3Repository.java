package com.vlc.maeummal.domain.template.template3.repository;

import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface Template3Repository extends JpaRepository<Template3Entity, Long>{
    Template3Entity save(Template3Entity template3);
    Optional<Template3Entity> findById(Long id);
    @Override
    List<Template3Entity> findAll();
}

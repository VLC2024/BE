package com.vlc.maeummal.domain.template.template1.repository;

import com.vlc.maeummal.domain.prep.prep1.entity.Prep1Entity;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Template1Repository extends JpaRepository<Template1Entity, Integer>{
    @Override
    List<Template1Entity> findAll();
}

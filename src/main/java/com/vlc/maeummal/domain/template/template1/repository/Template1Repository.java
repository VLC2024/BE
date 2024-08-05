package com.vlc.maeummal.domain.template.template1.repository;

import com.vlc.maeummal.domain.prep.prep1.entity.Prep1Entity;
import com.vlc.maeummal.domain.template.common.TemplateRepository;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Template1Repository extends JpaRepository<Template1Entity, Integer>{
}

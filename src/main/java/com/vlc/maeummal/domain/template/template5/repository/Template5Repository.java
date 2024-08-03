package com.vlc.maeummal.domain.template.template5.repository;

import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Template5Repository extends JpaRepository<Template5Entity, Long> {
    Template5Entity save(Template5Entity template5);
}

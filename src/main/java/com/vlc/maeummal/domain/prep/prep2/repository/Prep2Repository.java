package com.vlc.maeummal.domain.prep.prep2.repository;

import com.vlc.maeummal.domain.prep.prep2.entity.Prep2Entity;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Prep2Repository extends JpaRepository<Prep2Entity, Long> {

}

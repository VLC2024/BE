package com.vlc.maeummal.domain.prep.prep1.repository;

import com.vlc.maeummal.domain.prep.prep1.entity.Prep1Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Prep1Repository extends JpaRepository<Prep1Entity, Integer> {
}

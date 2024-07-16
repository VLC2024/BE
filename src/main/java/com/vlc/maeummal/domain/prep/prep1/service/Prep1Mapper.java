package com.vlc.maeummal.domain.prep.prep1.service;

import com.vlc.maeummal.domain.prep.prep1.dto.Prep1DTO;
import com.vlc.maeummal.domain.prep.prep1.entity.Prep1Entity;
import org.springframework.stereotype.Component;

@Component
public class Prep1Mapper {

    public Prep1DTO toDTO(Prep1Entity entity) {
        Prep1DTO dto = new Prep1DTO();
        dto.setId(entity.getId());
        dto.setSituation(entity.getSituation());
        dto.setDetailedSituation(entity.getDetailedSituation());
        dto.setAnswer(entity.getAnswer());
        dto.setExplanation(entity.getExplanation());
        dto.setUid(entity.getUid());
        dto.setOption1(entity.getOption1());
        dto.setOption2(entity.getOption2());
        dto.setOption3(entity.getOption3());
        dto.setOption4(entity.getOption4());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    public Prep1Entity toEntity(Prep1DTO dto) {
        Prep1Entity entity = new Prep1Entity();
        entity.setId(dto.getId());
        entity.setSituation(dto.getSituation());
        entity.setDetailedSituation(dto.getDetailedSituation());
        entity.setAnswer(dto.getAnswer());
        entity.setExplanation(dto.getExplanation());
        entity.setUid(dto.getUid());
        entity.setOption1(dto.getOption1());
        entity.setOption2(dto.getOption2());
        entity.setOption3(dto.getOption3());
        entity.setOption4(dto.getOption4());
        entity.setImageUrl(dto.getImageUrl());
        return entity;
    }

}

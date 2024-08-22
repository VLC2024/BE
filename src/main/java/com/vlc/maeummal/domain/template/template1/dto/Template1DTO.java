package com.vlc.maeummal.domain.template.template1.dto;

import com.vlc.maeummal.domain.lesson.dto.WordDTO;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.global.enums.Category;
import com.vlc.maeummal.global.enums.TemplateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Template1DTO {
    private Long id;
    private String description;
    private Integer imageNum;
    private TemplateType templateType;
    private Long createrId;
    @Builder.Default
    private List<WordSetRequestDTO.GetWordDTO> words = new ArrayList<>();
}

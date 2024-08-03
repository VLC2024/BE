package com.vlc.maeummal.domain.template.template1.dto;

import com.vlc.maeummal.domain.lesson.dto.WordDTO;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.global.enums.Category;
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
    private Integer id;
    @Builder.Default
    private List<WordSetRequestDTO.GetWordDTO> words = new ArrayList<>();
}
package com.vlc.maeummal.domain.prep.prep1.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 프롬프트 요청 DTO
 *
 * @author : yu
 * @fileName : CompletionRequestDto
 * @since : 07/17/24
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletionRequestDto {

    private String model;

    private String prompt;

    @Builder
    CompletionRequestDto(String model, String prompt) {
        this.model = model;
        this.prompt = prompt;
    }

}
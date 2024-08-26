package com.vlc.maeummal.domain.prep.prep1.dto;

import com.amazonaws.services.s3.internal.eventstreaming.Message;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Message> messages;
    private String prompt;

    @Builder
    public CompletionRequestDto(String model, String prompt) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.prompt = prompt;
    }

}
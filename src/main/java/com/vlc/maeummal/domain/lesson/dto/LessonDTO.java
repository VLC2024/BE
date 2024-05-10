package com.vlc.maeummal.domain.lesson.dto;

import lombok.*;

import java.util.List;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonDTO {
    // 상세 페이지에 반환할 내용
    private String title;
    private String content;
    private String created_at;
    private String view;
    private String category;
    private String difficulty;
    private String field;
}

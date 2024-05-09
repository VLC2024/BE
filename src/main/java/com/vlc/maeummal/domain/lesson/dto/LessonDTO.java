package com.vlc.maeummal.domain.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonDTO {
    private String title;
    private String content;
    private String created_at;
    private String view;
    private String category;
    private String difficulty;
    private String field;
}

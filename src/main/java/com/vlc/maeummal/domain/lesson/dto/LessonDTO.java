package com.vlc.maeummal.domain.lesson.dto;

import lombok.*;

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
//    private String field;
}

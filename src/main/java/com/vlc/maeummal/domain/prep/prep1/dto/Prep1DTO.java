package com.vlc.maeummal.domain.prep.prep1.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Prep1DTO {
    private Integer id;
    private String situation;
    private String detailedSituation;
    private String answer;
    private String explanation;
    private Long uid;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String imageUrl;
}

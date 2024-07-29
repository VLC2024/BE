package com.vlc.maeummal.domain.prep.prep2.dto;

import lombok.*;
import org.springframework.http.ResponseEntity;


public class Prep2ResponseDTO {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class generatedWordsDTO{

        private String noun1;
        private String noun2;
        private String noun3;
        private String noun4;
        private String verb1;
        private String verb2;
        private String verb3;
        private String verb4;
        private String adv1;
        private String adv2;
        private String adv3;
        private String adv4;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public  static class getImageDTO{
        private String sentence;
        private String img;
    }

}
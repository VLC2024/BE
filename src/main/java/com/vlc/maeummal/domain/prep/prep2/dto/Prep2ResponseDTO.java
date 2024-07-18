package com.vlc.maeummal.domain.prep.prep2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class Prep2ResponseDTO {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class getPromptDTO{

        private String prompt;
        private String noun1;
        private String noun2;
        private String noun3;
        private String verb1;
        private String verb2;
        private String verb3;
        private String adv1;
        private String adv2;
        private String adv3;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public  static class makeImg{
        private String img;
        private String sentence;
    }

}
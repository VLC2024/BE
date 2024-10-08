package com.vlc.maeummal.domain.prep.prep2.dto;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.util.List;


public class Prep2ResponseDTO {

    /*@Getter
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
    }*/

    public static class generatedWordsDTO {
        private List<String> nouns;
        private List<String> verbs;
        private List<String> adverbs;

        // Getter 및 Setter 메서드
        public List<String> getNouns() {
            return nouns;
        }

        public void setNouns(List<String> nouns) {
            this.nouns = nouns;
        }

        public List<String> getVerbs() {
            return verbs;
        }

        public void setVerbs(List<String> verbs) {
            this.verbs = verbs;
        }

        public List<String> getAdverbs() {
            return adverbs;
        }

        public void setAdverbs(List<String> adverbs) {
            this.adverbs = adverbs;
        }
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
package com.vlc.maeummal.domain.prep.prep2.dto;

import com.vlc.maeummal.global.enums.Category;
import lombok.Getter;
import lombok.Setter;

public class Prep2RequestDTO {

    @Getter
    @Setter
    public static class CategoryRequestDTO {
        private Category category;
    }

    @Getter
    @Setter
    public static class RequestDTO {

        private String noun;
        private String verb;
        private String adv;
    }
}
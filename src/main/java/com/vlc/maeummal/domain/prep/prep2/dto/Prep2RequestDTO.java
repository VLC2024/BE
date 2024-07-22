package com.vlc.maeummal.domain.prep.prep2.dto;

import com.vlc.maeummal.global.enums.Category;
import lombok.Getter;
import lombok.Setter;

public class Prep2RequestDTO {

    @Getter
    @Setter
    public static class GetCategoryDTO {
        Category category;
    }

    @Getter
    @Setter
    public static class GetWordDTO {

        private String noun;
        private String verb;
        private String adv;
    }
}

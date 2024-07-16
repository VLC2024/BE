package com.vlc.maeummal.domain.prep.prep2.dto;

import com.vlc.maeummal.global.enums.Category;
import lombok.Getter;
import lombok.Setter;

public class Prep2RequestDTO {
    @Getter
    @Setter
    public static class CategoryRequestDTO {

        private Category category;

        public Category getCategory() {
            return category;
        }
    }
}

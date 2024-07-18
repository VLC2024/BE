package com.vlc.maeummal.domain.word.dto;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class WordSetRequestDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordSetDTO {

        String title;
        String description;
        Category category;

        public static WordSetRequestDTO.GetWordSetDTO getWordSetDTO(WordSetRequestDTO.GetWordSetDTO wordSet) {
            return GetWordSetDTO.builder()
                    .category(wordSet.category)
                    .description(wordSet.description)
                    .title(wordSet.title)
                    .build();
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordDTO {
        String meaning;
        String prompt;
        String description;

        String image;

        public static WordSetRequestDTO.GetWordDTO getWordDTO(WordSetRequestDTO.GetWordDTO wordDTO) {
            return GetWordDTO.builder()
                    .meaning(wordDTO.meaning)
                    .image(wordDTO.image)
                    .prompt(wordDTO.prompt)
                    .description(wordDTO.description)
                    .build();
        }
    }
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WordSetCreationRequestDTO {
        private GetWordSetDTO wordSetDTO;
        private List<GetWordDTO> wordDTOList;
    }

}

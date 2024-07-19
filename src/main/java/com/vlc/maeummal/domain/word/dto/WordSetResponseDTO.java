package com.vlc.maeummal.domain.word.dto;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class WordSetResponseDTO {
    //내가 매칭 신청 보낸 리스트
    /**
     * WordSet {
     *     단어 set 기본 설명
     *     {
     *         단어 리스트
     *     }
     * }
     * 조회, 생성 시 이용
     *
     */

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordSetDTO {
        List<GetWordDTO> wordList;

        Long wordSetId;
        String title;
        String description;
        Category category;

        public static GetWordSetDTO getWordSetDTO(WordSetEntity wordSet) {
            List<GetWordDTO> getWordDTOList = wordSet.getWordEntities().stream()
                    .map(GetWordDTO::getWordDTO).toList();
            return GetWordSetDTO.builder()
                    .wordSetId(wordSet.getId())
                    .wordList(getWordDTOList)
                    .title(wordSet.getTitle())
                    .description(wordSet.getDescription())
                    .category(wordSet.getCategory())
                    .build();
        }

    }

    /**
     *
     * 단어 dto
     * 단어 set 생성 시 사용됨.
     *
     * */

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetWordDTO{
        Long wordId;
        String meaning;
        String prompt;
        String description;
        String image;


        public static GetWordDTO getWordDTO (WordEntity wordEntity) {
            return GetWordDTO.builder()
                        .wordId(wordEntity.getId())
                        .meaning(wordEntity.getMeaning())
                        .image(wordEntity.getImage())
                        .prompt(wordEntity.getPrompt())
                        .description(wordEntity.getDescription())
                        .build();
        }
    }
}

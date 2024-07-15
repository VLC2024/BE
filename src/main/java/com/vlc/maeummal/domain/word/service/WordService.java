package com.vlc.maeummal.domain.word.service;

import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.repository.WordSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class WordService {
    @Autowired
    WordSetRepository wordSetRepository;

    // id -> wordset반환
    public WordSetResponseDTO.GetWordSetDTO getWordSet(Long setId) {
        // db에서 가져옴, dto변환
        WordSetResponseDTO.GetWordSetDTO wordSetResponseDTO = WordSetResponseDTO.GetWordSetDTO.getWordSetDTO(wordSetRepository.findById(setId).get());

        // return
        return wordSetResponseDTO;

    }

    // WordSetRequestDTO.GetWordSetDTO wordSetDTO, List<WordSetRequestDTO.GetWordDTO> WordDTOList
    @Transactional
    public WordSetEntity saveWordSetWithWords(WordSetRequestDTO.GetWordSetDTO wordSetDTO, List<WordSetRequestDTO.GetWordDTO> wordDTOList) {
        // Step 1: Map DTO to EntitywordSetDT
        WordSetEntity wordSetEntity = WordSetEntity.builder()
                .title(wordSetDTO.getTitle())
                .description(wordSetDTO.getDescription())
                .category(wordSetDTO.getCategory())
                .wordEntities(new ArrayList<>())
                .build();
        // Step 2: Save WordSetEntity
        WordSetEntity savedWordSet = wordSetRepository.save(wordSetEntity);

        // Step 3: Map WordDTOs to WordEntities and associate with WordSetEntity
        for (WordSetRequestDTO.GetWordDTO wordDTO : wordDTOList) {
            WordEntity wordEntity = WordEntity.builder()
                    .meaning(wordDTO.getMeaning())
                    .image(wordDTO.getImage())
                    .prompt(wordDTO.getPrompt())
                    .description(wordDTO.getDescription())
                    .wordSet(savedWordSet) // Associate with the saved WordSetEntity
                    .build();

            // Add each WordEntity to the saved WordSetEntity
            savedWordSet.getWordEntities().add(wordEntity);
            // Step 4: Save WordEntities
            wordSetRepository.save(savedWordSet);
        }
            return savedWordSet;
        }


}

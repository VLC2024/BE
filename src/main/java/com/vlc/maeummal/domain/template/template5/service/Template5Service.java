package com.vlc.maeummal.domain.template.template5.service;

import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class Template5Service {
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private Template5Repository template5Repository;
    // 1. 선택한 단어장 가져오기 : 선택한 단어장 ID list -> 선택한 단어장의 단어 ID list
    public Template5ResponseDTO.GetWordIdListDTO getSelectedWordSetList(Template5RequestDTO.GetSelectedWordSetDTO wordSetIdDTO){
        // 단어장ID로 단어장 조회하여 단어장에 있는 단어들만 추출
//        Template5Entity template5 = new Template5Entity();
//        template5 = template5Repository.save(template5); // 저장 후에 ID가 할당됨
        List<Long> wordSetIdList = wordSetIdDTO.getWordSetIdList();
        List<Long> wordIdList = new ArrayList<>();

        for (Long wordSetId : wordSetIdList){
            List<WordEntity> wordList = wordRepository.findByWordSetId(wordSetId);
            for (WordEntity word : wordList){
                wordIdList.add(word.getId());
            }
        }

        Template5ResponseDTO.GetWordIdListDTO wordIdListDTO
                = Template5ResponseDTO.GetWordIdListDTO.builder()
//                .temp5_id(template5.getId())
                .wordIdList(wordIdList)
                .build();

        return wordIdListDTO;
    }
    public Template5ResponseDTO.GetWordListDTO randomWords(Template5ResponseDTO.GetWordIdListDTO wordIdList) {
        // wordIdList에서 랜덤으로 3개 뽑기
        List<Long> wordId = wordIdList.getWordIdList();
        Collections.shuffle(wordId);
        List<Long> randomWordIdList = wordId.subList(0, 3);
        List<WordEntity> selectedWordEntities = new ArrayList<>();
        List<WordSetResponseDTO.GetWordDTO> selectedWordList = new ArrayList<>();

        for (Long randomWordId : randomWordIdList) {
            Optional<WordEntity> optionalWordEntity = wordRepository.findById(randomWordId);
            if (optionalWordEntity.isPresent()) {
                WordEntity wordEntity = optionalWordEntity.get();
                selectedWordEntities.add(wordEntity);

                WordSetResponseDTO.GetWordDTO wordDTO = WordSetResponseDTO.GetWordDTO.getWordDTO(wordEntity);
                selectedWordList.add(wordDTO);
            } else {
                throw new RuntimeException("WordEntity not found for id: " + randomWordId);
            }
        }

        // DTO에 단어 리스트 설정
        Template5ResponseDTO.GetWordListDTO wordListDTO = Template5ResponseDTO.GetWordListDTO.builder()
                .wordList(selectedWordList)
                .build();

        // Template5Entity에 저장
        saveTemplate5Entity(selectedWordEntities);

        return wordListDTO;
    }

    private void saveTemplate5Entity(List<WordEntity> wordEntities) {
        Template5Entity template5Entity = new Template5Entity();
        template5Entity.setWordEntities(wordEntities);
        template5Repository.save(template5Entity);
    }
}

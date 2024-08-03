package com.vlc.maeummal.domain.template.template5.service;

import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordListEntity;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import com.vlc.maeummal.domain.template.template5.repository.WordListRepository;
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
    @Autowired
    private WordListRepository wordListRepository;

    // 1. 선택한 단어장 가져오기 : 선택한 단어장 ID list -> 선택한 단어장의 단어 ID list
    public List<Long> getSelectedWordSetList(Template5RequestDTO.GetSelectedWordSetDTO wordSetIdDTO) {
        // 단어장ID로 단어장 조회하여 단어장에 있는 단어들만 추출
        List<Long> wordSetIdList = wordSetIdDTO.getWordSetIdList();
        List<Long> wordIdList = new ArrayList<>();

        for (Long wordSetId : wordSetIdList) {
            List<WordEntity> wordList = wordRepository.findByWordSetId(wordSetId);
            for (WordEntity word : wordList) {
                wordIdList.add(word.getId());
            }
        }

        return wordIdList;
    }

    public Template5ResponseDTO.GetTemplate5DTO randomWords(List<Long> wordIdList) {
        // wordIdList에서 랜덤으로 3개 뽑기
        Collections.shuffle(wordIdList);
        List<Long> randomWordIdList = wordIdList.subList(0, 3);
        List<WordEntity> selectedWordEntities = new ArrayList<>();
        List<WordSetResponseDTO.GetWordDTO> selectedWordList = new ArrayList<>();

        for (Long randomWordId : randomWordIdList) {
            Optional<WordEntity> optionalWordEntity = wordRepository.findById(randomWordId); // wordId -> wordEntity
            if (optionalWordEntity.isPresent()) {
                WordEntity wordEntity = optionalWordEntity.get();
                selectedWordEntities.add(wordEntity);

                WordSetResponseDTO.GetWordDTO wordDTO = WordSetResponseDTO.GetWordDTO.getWordDTO(wordEntity);
                selectedWordList.add(wordDTO);
            } else {
                throw new RuntimeException("WordEntity not found for id: " + randomWordId);
            }
        }

        // Template5Entity에 저장, word에는 어떻게 저장되는 것인지, 하나의 word가 여러 temp5에 사용될 수 있음.
        // Template5Entity에 저장
        Template5Entity savedTemplate5Entity = saveTemplate5Entity(selectedWordEntities);

        // DTO에 단어 리스트 설정. DB에서 가져오도록 구현
        Template5ResponseDTO.GetTemplate5DTO wordListDTO = Template5ResponseDTO.GetTemplate5DTO.builder()
                .temp5_id(savedTemplate5Entity.getId())
                .wordList(selectedWordList)
                .build();

        return wordListDTO;
    }

    /*private Template5Entity saveTemplate5Entity(List<WordEntity> wordEntities) {
        Template5Entity template5Entity = new Template5Entity();
        template5Entity.setWordListEntities(wordEntities);
        template5Repository.save(template5Entity);
        return template5Entity;
    }*/

    private Template5Entity saveTemplate5Entity(List<WordEntity> wordEntities) {
        // Template5Entity 생성 및 저장
        Template5Entity template5Entity = new Template5Entity();
        Template5Entity savedTemplate5Entity = template5Repository.save(template5Entity);

        // WordListEntity 저장
        for (WordEntity wordEntity : wordEntities) {
            WordListEntity wordListEntity = new WordListEntity();
            wordListEntity.setTemp5(savedTemplate5Entity);
            wordListEntity.setWord(wordEntity);
            wordListRepository.save(wordListEntity);
        }

        return savedTemplate5Entity;
    }

    // id를 통해
    public Template5ResponseDTO.GetTemplate5DTO getTemplate5(Long temp5Id) {
        List<WordSetResponseDTO.GetWordDTO> wordDTOList = new ArrayList<>();

        // 1. temp5_id를 이용해 temp5_word 테이블에서 word id 접근
        List<WordListEntity> wordListEntities = wordListRepository.findByTemp5_Id(temp5Id);

        // 2. word id DTO로 변환
        for (WordListEntity wordListEntity : wordListEntities) {
            WordEntity wordEntity = wordListEntity.getWord();
            WordSetResponseDTO.GetWordDTO wordDTO = WordSetResponseDTO.GetWordDTO.getWordDTO(wordEntity);
            wordDTOList.add(wordDTO);
        }

        return Template5ResponseDTO.GetTemplate5DTO.builder()
                .temp5_id(temp5Id)
                .wordList(wordDTOList)
                .build();
    }
}

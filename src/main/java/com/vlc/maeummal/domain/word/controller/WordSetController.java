package com.vlc.maeummal.domain.word.controller;

import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.service.WordService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vlc.maeummal.global.apiPayload.ApiResponse.onSuccess;

@Controller
@RequestMapping("word")
public class WordSetController {
    @Autowired
    WordService wordService;


    @GetMapping("/wordSet")
    public ResponseEntity<?> getWordSet(@RequestParam Long wordSetId){
        WordSetResponseDTO.GetWordSetDTO wordSet = wordService.getWordSet(wordSetId);
        if (wordSet == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("낱말카드", "해당하는 낱말 카드가 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(wordSet));
    }

//    @PostMapping("/wordSet")
//    public ResponseEntity<?> getWordSet(@RequestBody WordSetRequestDTO.GetWordSetDTO wordSetDTO, @RequestBody List<WordSetRequestDTO.GetWordDTO> WordDTOList){
//        // wordSet를 저장
//        wordService.saveWordSetWithWords(wordSetDTO, WordDTOList);
//        return ResponseEntity.ok(ApiResponse.successWithoutResult());
//    }
    @PostMapping("/wordSet")
    public ResponseEntity<?> createWordSet(@RequestBody WordSetRequestDTO.WordSetCreationRequestDTO requestDTO) {
        wordService.saveWordSetWithWords(requestDTO.getWordSetDTO(), requestDTO.getWordDTOList());

        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }

}

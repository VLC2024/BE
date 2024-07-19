package com.vlc.maeummal.domain.word.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.service.WordService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/word")
@RequiredArgsConstructor
public class WordSetController {

    private final WordService wordService;

    private final AmazonS3 s3Client;

    @GetMapping("/wordSet")
    public ResponseEntity<?> getWordSet(@RequestParam Long wordSetId){
        WordSetResponseDTO.GetWordSetDTO wordSet = wordService.getWordSet(wordSetId);
        if (wordSet == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("낱말카드", "해당하는 낱말 카드가 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(wordSet));
    }
    @GetMapping("/wordSet/all")
    public ResponseEntity<?> getAllWordSet(){
        List<WordSetResponseDTO.GetWordSetDTO> wordSetDTOList = wordService.getAllWordSet();

        return ResponseEntity.ok(ApiResponse.onSuccess(wordSetDTOList));
    }

    @PostMapping("/wordSet")
    public ResponseEntity<?> createWordSet(@RequestBody WordSetRequestDTO.WordSetCreationRequestDTO wordSet){
        // wordSet를 저장
        wordService.saveWordSetWithWords(wordSet.getWordSetDTO(), wordSet.getWordDTOList());
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }

    /**
     * word 생성을 위해 문장 전달
     * request : meaning
     * return : image
     * 임시로 s3저장된 이미지 return
     * */
    @PostMapping("/image")
    public ResponseEntity<?> getWordImgageFromS3(@RequestBody String meaning) throws IOException {

        // dalle 이미지 생성

        // 이미지 저장 in s3
        String imageUrl = wordService.saveWordImageInS3AndGetUrl_Test();

        // return image url
        return ResponseEntity.ok(ApiResponse.onSuccess(imageUrl));

    }


}

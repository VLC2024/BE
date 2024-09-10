package com.vlc.maeummal.domain.word.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.service.WordService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.status.SuccessStatus;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.MissionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
@Slf4j
@Controller
@RequestMapping("/word")
@RequiredArgsConstructor
public class WordSetController {

    private final WordService wordService;

    private final AmazonS3 s3Client;
    private final UserAuthorizationConverter userAuthorizationConverter;

    @GetMapping("/wordSet")
    public ResponseEntity<?> getWordSet(@RequestParam Long wordSetId){
        WordSetResponseDTO.GetWordSetDTO wordSet = wordService.getWordSet(wordSetId);

        if (wordSet == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("낱말카드", "해당하는 낱말 카드가 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(wordSet));
    }
    @GetMapping("/myWordSet")
    public ResponseEntity<?> getMyWordSet(){
        Long currentUserId = userAuthorizationConverter.getCurrentUserId();
        List<WordSetResponseDTO.GetWordSetDTO> wordSetDTOList = wordService.getTeacherWordSet(currentUserId);

        return ResponseEntity.ok(ApiResponse.onSuccess(wordSetDTOList));
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

    /** Test code
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
    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<WordSetEntity>>> retrieveWordSetFromTitleContaining(
            @RequestParam(value = "title") String title) {
        List<WordSetEntity> wordSets = wordService.getAllWordSetFromTitleContaining(title);
        return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, wordSets));
    }


}
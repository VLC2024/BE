package com.vlc.maeummal.domain.prep.prep2.controller;

import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.service.Prep2Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.Category;
import com.vlc.maeummal.global.enums.MissionType;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("prep2")
@AllArgsConstructor
public class Prep2Controller {

    final private Prep2Service prep2Service;

    final private AiService aiService;
    final private UserAuthorizationConverter userAuthorizationConverter;
    final private ChallengeService challengeService;

//    @PostMapping("/")
//    public ResponseEntity<?> getWords (@RequestBody Prep2RequestDTO.GetCategoryDTO requestDTO) {
//
//        String category = requestDTO.getCategory().toString();
////        Prep2ResponseDTO.generatedWordsDTO responseDTO = prep2Service.generateWords(category);
//        Prep2ResponseDTO.generatedWordsDTO responseDTO = prep2Service.(category);
//        return ResponseEntity.ok(ApiResponse.onSuccess(responseDTO));
//    }

    @PostMapping("/")
    public ResponseEntity<?> getWords(@RequestBody Prep2RequestDTO.GetCategoryDTO requestDTO) {
        // 요청에서 카테고리 추출
        Category category = requestDTO.getCategory();

        // 카테고리에 맞는 단어들을 생성
        Prep2ResponseDTO.generatedWordsDTO responseDTO = prep2Service.generateWordsByCategory(category);

        // 만약 단어 생성에 실패하면(유효한 값이 없으면), 에러 응답을 보냄
        if (responseDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to generate valid words after 3 attempts.");
        }

        // 성공적인 응답 반환
        return ResponseEntity.ok(ApiResponse.onSuccess(responseDTO));
    }
    @PostMapping("/result")
    public ResponseEntity<?> getImageFromS3(@RequestBody Prep2RequestDTO.GetWordDTO requestDTO) {
        String sentence = prep2Service.makeSentence(requestDTO);
        String prompt = prep2Service.translateToEnglish(sentence);
        String base64ImageData = aiService.generatePicture(prompt); // base64 data
        String imageUrl = null;

        if (!SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
            challengeService.completeMission(userAuthorizationConverter.getCurrentUserId(), MissionType.PREP);
        }

        //  Base64 데이터를 MultipartFile로 변환하여 S3에 업로드
        try {
            imageUrl = aiService.uploadImageToS3(base64ImageData);
            log.info("Image uploaded successfully to S3. URL: {}", imageUrl);

            Prep2ResponseDTO.getImageDTO responseDTO = new Prep2ResponseDTO.getImageDTO(sentence, imageUrl);
            return ResponseEntity.ok(ApiResponse.onSuccess(responseDTO));

        } catch (IOException e) {
            log.error("Failed to upload image to S3", e);

            String errorMessage = "Failed to upload image to S3";
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure(errorMessage, e.getMessage(), requestDTO));
        }
    }
}

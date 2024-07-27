package com.vlc.maeummal.domain.prep.prep2.controller;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.service.Prep2Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.aws.service.S3Service;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@Slf4j
@RestController
@RequestMapping("prep2")
public class Prep2Controller {
    @Autowired
    private Prep2Service prep2Service;
    @Autowired
    private AiService aiService;
    @Autowired
    private S3Service s3Service;

    @PostMapping("/")
    public ResponseEntity<?> getWords (@RequestBody Prep2RequestDTO.GetCategoryDTO requestDTO) {

        String category = requestDTO.getCategory().toString();
        Prep2ResponseDTO.generatedWordsDTO responseDTO = prep2Service.generateWords(category);

        return ResponseEntity.ok(ApiResponse.onSuccess(responseDTO));
    }

    @PostMapping("/result")
    public ResponseEntity<?> getImageFromS3(@RequestBody Prep2RequestDTO.GetWordDTO requestDTO) {
        String sentence = prep2Service.makeSentence(requestDTO);
        String base64ImageData = aiService.generatePicture(sentence); // base64 data
        String imageUrl = null;

        //  Base64 데이터를 MultipartFile로 변환하여 S3에 업로드
        try {
            imageUrl = s3Service.uploadImageToS3(base64ImageData);
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

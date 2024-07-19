package com.vlc.maeummal.global.openAi.dalle.service;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AiService {

    @Resource(name = "")
    private final OpenAiService openAiService;

    public String generatePicture(String prompt) {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("512x512")
                .n(1)
                .responseFormat("b64_json") // multipartfile로 변한하기 위해
                .build();

        // 이미지 생성 API 호출 및 Base64 데이터 추출
        String base64ImageData = openAiService.createImage(createImageRequest).getData().get(0).getB64Json();
//        System.out.println(base64ImageData);
        return base64ImageData;
    }

}
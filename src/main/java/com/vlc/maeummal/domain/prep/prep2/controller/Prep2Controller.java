package com.vlc.maeummal.domain.prep.prep2.controller;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.service.Prep2Service;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.openAi.chatGPT.service.ChatGPTService;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("prep2")
public class Prep2Controller {
    @Autowired
    private Prep2Service prep2Service;
    @Autowired
    private AiService aiService;

    @PostMapping("/")
    public ResponseEntity<?> getPrompt(@RequestBody Prep2RequestDTO.GetCategoryDTO requestDTO) {

        String category = requestDTO.getCategory().toString();
        Prep2ResponseDTO.GeneratedWordsDTO responseDTO = prep2Service.saveDTO(category);

        return ResponseEntity.ok(ApiResponse.onSuccess(responseDTO));
    }

    @PostMapping("/image")
    public Prep2ResponseDTO.getImageDTO generateImage(@RequestBody Prep2RequestDTO.GetWordDTO requestDTO) {
        String sentence = prep2Service.generateSentence(requestDTO);
        String image = aiService.generatePicture(sentence);

        return new Prep2ResponseDTO.getImageDTO(sentence, image);
    }
}

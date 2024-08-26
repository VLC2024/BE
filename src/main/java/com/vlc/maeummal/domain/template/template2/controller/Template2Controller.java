package com.vlc.maeummal.domain.template.template2.controller;

import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template2.dto.Template2ResponseDTO;
import com.vlc.maeummal.domain.template.template2.service.Template2Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/template2")
@RequiredArgsConstructor
public class Template2Controller {

    private final Template2Service template2Service;

    @GetMapping("/get")
    public ResponseEntity<?> getTemplate2(@RequestParam Long template2Id) {
        Template2ResponseDTO.GetTemplate2DTO template2 = template2Service.getTemplate2(template2Id);
        if (template2 == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿이 없습니다.", null));
        }


        return ResponseEntity.ok(ApiResponse.onSuccess(template2));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTemplate2() {
        List<Template2ResponseDTO.GetTemplate2DTO> template2DTOList = template2Service.getAllTemplate2();
        return ResponseEntity.ok(ApiResponse.onSuccess(template2DTOList));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTemplate2(@RequestBody Template2RequestDTO.GetTemplate2DTO template2) {
        template2Service.saveTemplate2WithStoryCards(template2, template2.getStoryCardEntityList());
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }
}

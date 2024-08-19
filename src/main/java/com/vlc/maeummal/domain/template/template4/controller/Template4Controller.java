package com.vlc.maeummal.domain.template.template4.controller;

import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template2.service.Template2Service;
import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.service.Template4Service;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template4")
@RequiredArgsConstructor
public class Template4Controller {
    private final Template4Service template4Service;
    @PostMapping("/create")
    public ResponseEntity<?> createTemplate4(@RequestBody Template4RequestDTO.GetTemplate4DTO template4DTO) {
        template4Service.createTemplate4(template4DTO, template4DTO.getStoryCardEntityList());
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }
}

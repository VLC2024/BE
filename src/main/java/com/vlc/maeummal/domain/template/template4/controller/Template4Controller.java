package com.vlc.maeummal.domain.template.template4.controller;

import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.dto.Template4ResponseDTO;
import com.vlc.maeummal.domain.template.template4.service.Template4Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get")
    public ResponseEntity<?> getTemplate4(@RequestParam Long template4Id) {
        Template4ResponseDTO.GetTemplate4DTO template4 = template4Service.getTemplate4(template4Id);
        if (template4 == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿이 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(template4));
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateTemplate4(@RequestParam Long template4Id, @RequestBody Template4RequestDTO.GetTemplate4DTO template4DTO) {
        try {
            template4Service.updateTemplate4(template4Id, template4DTO, template4DTO.getStoryCardEntityList());
            return ResponseEntity.ok(ApiResponse.successWithoutResult());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "템플릿을 찾을 수 없습니다.", null));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTemplate4(@RequestParam Long template4Id) {
        try {
            template4Service.deleteTemplate4(template4Id);
            return ResponseEntity.ok(ApiResponse.successWithoutResult());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "템플릿을 찾을 수 없습니다.", null));
        }
    }
}

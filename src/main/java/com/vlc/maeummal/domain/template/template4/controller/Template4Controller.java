package com.vlc.maeummal.domain.template.template4.controller;

import com.vlc.maeummal.domain.member.dto.MemberDTO;
import com.vlc.maeummal.domain.template.template4.dto.ImageUploadDTO;
import com.vlc.maeummal.domain.template.template4.dto.Template4RequestDTO;
import com.vlc.maeummal.domain.template.template4.dto.Template4ResponseDTO;
import com.vlc.maeummal.domain.template.template4.service.Template4Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/upload")
    public ResponseEntity<?> updateImage(@RequestParam @Parameter(description = "temp4 ID") Long id, @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("업로드할 이미지가 없습니다. 이미지 업로드에 실패했습니다.");
        }

        ImageUploadDTO result = template4Service.uploadImage(id,file);
        return ResponseEntity.ok(ApiResponse.onSuccess(result));
    }


    @GetMapping("/get")
    public ResponseEntity<?> getTemplate4(@RequestParam(value = "template4Id", required = false) Long template4Id) {
        if (template4Id == null) {
            // template4Id가 없으면 전체 템플릿을 불러옴
            List<Template4ResponseDTO.GetTemplate4DTO> template4List = template4Service.getAllTemplate4();
            if (template4List == null || template4List.isEmpty()) {
                return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "템플릿 목록이 없습니다.", null));
            }
            return ResponseEntity.ok(ApiResponse.onSuccess(template4List));
        } else {
            // template4Id가 있으면 특정 템플릿을 불러옴
            Template4ResponseDTO.GetTemplate4DTO template4 = template4Service.getTemplate4(template4Id);
            if (template4 == null) {
                return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿이 없습니다.", null));
            }
            return ResponseEntity.ok(ApiResponse.onSuccess(template4));
        }
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

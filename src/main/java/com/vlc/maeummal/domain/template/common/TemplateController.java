package com.vlc.maeummal.domain.template.common;

import com.vlc.maeummal.domain.template.common.dto.TemplateResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.status.SuccessStatus;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RestController
@RequestMapping("/templates")
@AllArgsConstructor
public class TemplateController {


    private final TemplateService templateService;

    private final UserAuthorizationConverter userAuthorizationConverter;

    @GetMapping("/all")
    public ApiResponse<List<TemplateResponseDTO.GetTemplates>>getAllTemplates() {
        return ApiResponse.onSuccess(templateService.getAllTemplateList());
    }
    @GetMapping("/recent")
    public ApiResponse<List<TemplateResponseDTO.GetTemplates>> getRecentTemplates() {
        return ApiResponse.onSuccess(templateService.getRecentTemplates());

    }
    @GetMapping("/retrieve/title")
    public ApiResponse<List<TemplateResponseDTO.GetTemplates>> retrieveTemplateUsingTitle(
            @RequestParam(value = "title") String title
    ) {
        return ApiResponse.onSuccess(templateService.retrieveTemplateUsingTitle(title));

    }

    @GetMapping("/user")
    public ResponseEntity<List<TemplateResponseDTO.GetTemplates>> getTemplatesByUserId() {

        // 현재 로그인된 선생님의 ID를 사용
        Long createrId = userAuthorizationConverter.getCurrentUserId();

        // 서비스 메서드 호출하여 결과 가져오기
        List<TemplateResponseDTO.GetTemplates> templates = templateService.getTemplatesByUserId(createrId);
        return ResponseEntity.ok(templates); // 200 OK 응답과 함께 반환
    }
    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<TemplateResponseDTO.GetTemplates>>> retrieveTemplateFromTitleContaining(
            @RequestParam(value = "title") String title) {
        List<TemplateResponseDTO.GetTemplates> templateEntities = templateService.retrieveTemplateUsingTitle(title);
        return ResponseEntity.ok(ApiResponse.of(SuccessStatus._OK, templateEntities));
    }

}

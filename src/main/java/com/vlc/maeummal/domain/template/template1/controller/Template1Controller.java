package com.vlc.maeummal.domain.template.template1.controller;

import com.vlc.maeummal.domain.template.template1.dto.Template1DTO;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template1.service.Template1Service;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/temp1")
@RequiredArgsConstructor
public class Template1Controller {
    private final Template1Service template1Service;
    private final UserAuthorizationConverter userAuthorizationConverter;

    @PostMapping("/create")
    public ResponseEntity<Template1DTO> createTemplate() {
        Template1DTO createdTemplate = template1Service.createTemplate();
        return ResponseEntity.ok(createdTemplate);
    }

    @PostMapping("/{id}/add-random-words")
    public ResponseEntity<Template1DTO> addRandomWordsToTemplate(@PathVariable Long id) {
        Template1DTO updatedTemplate = template1Service.addRandomWordsToTemplate(id);
        return ResponseEntity.ok(updatedTemplate);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Template1DTO>> getAllTemplates() {
        List<Template1DTO> templates = template1Service.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

    @GetMapping("/get")
    public ResponseEntity<Template1DTO> getTemplateById(@RequestParam Long temp1Id) {
        Template1DTO template = template1Service.getTemplateById(temp1Id);
        return ResponseEntity.ok(template);
    }

    @GetMapping("/by-creater")
    public ResponseEntity<List<Template1DTO>> getTemplatesByCreaterId() {
        // 현재 로그인된 선생님의 ID를 사용
        Long createrId = userAuthorizationConverter.getCurrentUserId();

        List<Template1DTO> templates = template1Service.getTemplatesByCreaterId(createrId);
        return ResponseEntity.ok(templates);
    }

}

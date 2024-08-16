package com.vlc.maeummal.domain.template.common;

import com.vlc.maeummal.domain.template.common.dto.TemplateResponseDTO;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Controller
@RestController
@RequestMapping("/api/templates")
@AllArgsConstructor
public class TemplateController {


    private final TemplateService templateService;

    @GetMapping("/all")
    public ApiResponse<List<TemplateResponseDTO.GetTemplates>>getAllTemplates() {
        return ApiResponse.onSuccess(templateService.getAllTemplateList());
    }
}

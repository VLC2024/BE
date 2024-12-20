package com.vlc.maeummal.domain.template.template5.controller;

import com.vlc.maeummal.domain.template.template1.dto.Template1DTO;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.service.Template5Service;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.service.WordService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/template5")
@RequiredArgsConstructor
public class Template5Controller {
    @Autowired
    private WordService wordService;
    @Autowired
    private Template5Service template5Service;
    //템플릿 수강 : template5/{temp5_id}
    /*
      템플릿5 생성 : template5/create_temp5/{temp5_id}
      [GET] Req: / Res: 전체 단어장 보여주기(단어장 저장이 구현되면 수정)
      [POST] Req: 선택한 단어장 ID List / Res: 추출한 단어 3개 + 템플릿 ID
     */

    @GetMapping("/create")
    public ResponseEntity<?> getAllWordSet(){
        List<WordSetResponseDTO.GetWordSetDTO> wordSetDTOList = wordService.getAllWordSet();

        return ResponseEntity.ok(ApiResponse.onSuccess(wordSetDTOList));
    }

    @PostMapping("/create")
    public ResponseEntity<?> getSelectedWordSet(@RequestBody Template5RequestDTO.GetTemplate5DTO dto){
        List<Long> wordIdListDTO = template5Service.getSelectedWordSetList(dto.getWordSetId());
        Template5ResponseDTO.GetTemplate5DTO wordListDTO = template5Service.randomWords(dto,wordIdListDTO);

        return ResponseEntity.ok(ApiResponse.onSuccess(wordListDTO));
    }
    // 템플릿 조회
    @GetMapping("/get")
    public ResponseEntity<?> getTemplate5(@RequestParam Long temp5Id){
        Template5ResponseDTO.GetTemplate5DTO template5 = template5Service.getTemplate5(temp5Id);
        if (template5 == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿이 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(template5));
    }

    // 전체 템플릿 조회
    @GetMapping("/all")
    public ResponseEntity<?> getAllTemplate5(){
        List<Template5ResponseDTO.GetTemplate5DTO> template5DTOList = template5Service.getAllTemplate5();
        return ResponseEntity.ok(ApiResponse.onSuccess(template5DTOList));
    }

    @GetMapping("/{templateId}/related-templates")
    public ResponseEntity<?> getRelatedTemplatesByTemplateId(@PathVariable Long templateId) {
        Template5ResponseDTO.GetTemplate5DTO relatedTemplates = template5Service.getRelatedTemplateByTemplateId(templateId);
        return ResponseEntity.ok(ApiResponse.onSuccess(relatedTemplates)); // 200 OK 응답과 함께 템플릿 리스트 반환
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateTemplate5(@RequestParam Long temp5Id, @RequestBody Template5RequestDTO.GetTemplate5DTO template5DTO) {
        try {
            List<Long> wordIdListDTO = template5Service.getSelectedWordSetList(template5DTO.getWordSetId());
            Template5ResponseDTO.GetTemplate5DTO updatedTemplate = template5Service.updateTemplate5(temp5Id, template5DTO, wordIdListDTO);
            return ResponseEntity.ok(ApiResponse.onSuccess(updatedTemplate));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿을 찾을 수 없습니다.", null));
        }
    }

    // 템플릿 삭제 (DELETE)
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteTemplate5(@RequestParam Long temp5Id) {
        try {
            template5Service.deleteTemplate5(temp5Id);
            return ResponseEntity.ok(ApiResponse.successWithoutResult());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿을 찾을 수 없습니다.", null));
        }
    }

}

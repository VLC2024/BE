package com.vlc.maeummal.domain.template.template2.controller;

import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template2.dto.Template2ResponseDTO;
import com.vlc.maeummal.domain.template.template2.service.Template2Service;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import com.vlc.maeummal.global.apiPayload.code.ErrorReasonDTO;
import com.vlc.maeummal.global.apiPayload.code.status.ErrorStatus;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    @PutMapping("/update/{template2Id}")
    public ResponseEntity<?> updateTemplate2(@PathVariable Long template2Id, @RequestBody Template2RequestDTO.GetTemplate2DTO template2) {
        try {
            template2Service.updateTemplate2WithStoryCards(template2Id, template2, template2.getStoryCardEntityList());
            return ResponseEntity.ok(ApiResponse.successWithoutResult());
        } catch (EntityNotFoundException exception){
//            return ResponseEntity.badRequest(ApiErrResponse.onFailure(ErrorStatus._NOT_FOUND_TEMPLATE.getMessage(),
//                    ErrorStatus._NOT_FOUND_TEMPLATE.getMessage(), null));
            return ResponseEntity.badRequest().body("해당 템플릿이 존재하지 않습니다.");
        }catch (AccessDeniedException exception) {
            return ResponseEntity.badRequest().body("작성자만 수정할 수 있습니다.");
        }

    }
    @DeleteMapping("/{template2Id}")
    public ResponseEntity<String> deleteTemplate2(@PathVariable Long template2Id) {
        try {
            template2Service.deleteTemplate2(template2Id);
            return new ResponseEntity<>("템플릿이 성공적으로 삭제되었습니다.", HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("템플릿을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        } catch (AccessDeniedException e) {
            return new ResponseEntity<>("자신의 템플릿만 삭제할 수 있습니다.", HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            return new ResponseEntity<>("템플릿 삭제 도중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}




















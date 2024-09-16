package com.vlc.maeummal.domain.template.template3.controller;

import com.amazonaws.services.s3.AmazonS3;
import com.vlc.maeummal.domain.template.template2.dto.Template2RequestDTO;
import com.vlc.maeummal.domain.template.template3.dto.Template3RequestDTO;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template3.service.Template3Service;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/template3")
@RequiredArgsConstructor
public class Template3Controller {

    private final Template3Service template3Service;

    private final AmazonS3 s3Client;

    @GetMapping("/get")
    public ResponseEntity<?> getTemplate3(@RequestParam Long template3Id) {
        Template3ResponseDTO.GetTemplate3DTO template3 = template3Service.getTemplate3(template3Id);
        if (template3 == null) {
            return ResponseEntity.badRequest().body(ApiErrResponse.onFailure("템플릿", "해당하는 템플릿이 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.onSuccess(template3));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllTemplate3() {
        List<Template3ResponseDTO.GetTemplate3DTO> template3DTOList = template3Service.getAllTemplate3();
        return ResponseEntity.ok(ApiResponse.onSuccess(template3DTOList));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTemplate3(@RequestBody Template3RequestDTO.Template3CreationRequestDTO template3) {
        // template3를 저장
        template3Service.saveTemplate3WithImageCards(template3.getTemplate3DTO(), template3.getImageCardDTOList());
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    }
    @PutMapping("/update/{template3Id}")
    public ResponseEntity<?> updateTemplate2(@PathVariable Long template3Id, @RequestBody Template3RequestDTO.Template3CreationRequestDTO template3) {
        try {
        template3Service.updateTemplate3WithImageCards(template3Id, template3.getTemplate3DTO(), template3.getImageCardDTOList());
        return ResponseEntity.ok(ApiResponse.successWithoutResult());
    } catch (EntityNotFoundException exception){
        return ResponseEntity.badRequest().body("해당 템플릿이 존재하지 않습니다.");
    }catch (AccessDeniedException exception) {
        return ResponseEntity.badRequest().body("작성자만 수정할 수 있습니다.");
    }
    }
    @DeleteMapping("/{template3Id}")
    public ResponseEntity<String> deleteTemplate3(@PathVariable Long template3Id) {
        try {
            template3Service.deleteTemplate3(template3Id);
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



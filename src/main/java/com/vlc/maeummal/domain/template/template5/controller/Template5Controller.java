package com.vlc.maeummal.domain.template.template5.controller;

import com.vlc.maeummal.domain.template.template5.dto.Template5RequestDTO;
import com.vlc.maeummal.domain.template.template5.dto.Template5ResponseDTO;
import com.vlc.maeummal.domain.template.template5.service.Template5Service;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.service.WordService;
import com.vlc.maeummal.global.apiPayload.ApiErrResponse;
import com.vlc.maeummal.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping("/create_template5")
    public ResponseEntity<?> getAllWordSet(){
        List<WordSetResponseDTO.GetWordSetDTO> wordSetDTOList = wordService.getAllWordSet();

        return ResponseEntity.ok(ApiResponse.onSuccess(wordSetDTOList));
    }

    @PostMapping("/create_template5")
    public ResponseEntity<?> getSelectedWordSet(@RequestBody Template5RequestDTO.GetSelectedWordSetDTO wordSetDTO){
        Template5ResponseDTO.GetWordIdListDTO wordIdListDTO = template5Service.getSelectedWordSetList(wordSetDTO);
        Template5ResponseDTO.GetWordListDTO wordListDTO = template5Service.randomWords(wordIdListDTO);

        return ResponseEntity.ok(ApiResponse.onSuccess(wordListDTO));
    }



    // 템플릿5 저장 : template5/save_new_temp5/{temp5_id}
    // Req: 추출한 단어 3개 + 템플릿 ID / Res: 저장되었습니다. or 실패했습니다.
}

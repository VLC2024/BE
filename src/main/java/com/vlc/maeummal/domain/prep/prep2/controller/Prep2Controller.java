package com.vlc.maeummal.domain.prep.prep2.controller;

import com.vlc.maeummal.domain.prep.prep2.dto.Prep2RequestDTO;
import com.vlc.maeummal.domain.prep.prep2.dto.Prep2ResponseDTO;
import com.vlc.maeummal.domain.prep.prep2.service.Prep2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("prep2")
public class Prep2Controller {

    private final Prep2Service prep2Service;

    @Autowired
    public Prep2Controller(Prep2Service prep2Service) {
        this.prep2Service = prep2Service;
    }

    @PostMapping("/")
    public Prep2ResponseDTO.getPromptDTO getPrompt(@RequestBody Prep2RequestDTO.CategoryRequestDTO requestDTO) {
        prep2Service.saveCategory(requestDTO);
        return prep2Service.getPrompt(requestDTO.getCategory().toString());
    }
}

package com.vlc.maeummal.domain.template.common;

import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.common.dto.TemplateResponseDTO;
import com.vlc.maeummal.domain.template.template1.repository.Template1Repository;
import com.vlc.maeummal.domain.template.template2.repository.Template2Repository;
import com.vlc.maeummal.domain.template.template3.repository.Template3Repository;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TemplateService {

//    private final Template1Repository template1Repository;
    private final Template2Repository template2Repository;
    private final Template3Repository template3Repository;
//    private final Template4Repository template4Repository;
    private final Template5Repository template5Repository;


    public List<TemplateResponseDTO.GetTemplates> getAllTemplateList(){

        List<TemplateEntity> templateEntityList = new ArrayList<>();
//        templateEntityList.addAll(template1Repository.findAll());
        templateEntityList.addAll(template2Repository.findAll());
        templateEntityList.addAll(template3Repository.findAll());
        templateEntityList.addAll(template5Repository.findAll());

        // 생성일 기준으로 오름차순 정렬
        Comparator<TemplateEntity> comparator = (o1, o2) -> {
            LocalDateTime createdAt1 = o1.getCreatedAt();
            LocalDateTime createdAt2 = o2.getCreatedAt();
            return createdAt1.compareTo(createdAt2); // 오름차순
        };

        // 리스트 정렬
        Collections.sort(templateEntityList, comparator);

        // Map to TemplateResponseDTO.GetTemplates using stream and collect
        return templateEntityList.stream()
                .map(TemplateResponseDTO.GetTemplates::convertTemplateResponseDTO) // Assuming GetTemplates is a constructor reference
                .collect(Collectors.toList());



    }
}

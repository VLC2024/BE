package com.vlc.maeummal.domain.template.template3.service;

import com.vlc.maeummal.domain.template.template3.dto.Template3RequestDTO;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template3.repository.ImageCardRepository;
import com.vlc.maeummal.domain.template.template3.repository.Template3Repository;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.UuidRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Template3Service {

    private final Template3Repository template3Repository;
    private final ImageCardRepository imageCardRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    // id를 통해 Template3 반환
    public Template3ResponseDTO.GetTemplate3DTO getTemplate3(Long template3Id) {
        // DB에서 가져와서 DTO로 변환
        Template3ResponseDTO.GetTemplate3DTO template3ResponseDTO = Template3ResponseDTO.GetTemplate3DTO.getTemplate3DTO(template3Repository.findById(template3Id).orElseThrow(() -> new EntityNotFoundException("Template not found")));

        // 반환
        return template3ResponseDTO;
    }

    public List<Template3ResponseDTO.GetTemplate3DTO> getAllTemplate3() {
        // DB에서 가져와서 DTO로 변환
        List<Template3Entity> template3EntityList = template3Repository.findAll();
        return template3EntityList.stream()
                .map(template3 -> Template3ResponseDTO.GetTemplate3DTO.getTemplate3DTO(template3))
                .collect(Collectors.toList());
    }

    @Transactional
    public Template3Entity saveTemplate3WithImageCards(Template3RequestDTO.GetTemplate3DTO template3DTO, List<Template3RequestDTO.GetImageCardDTO> imageCardDTOList) {
        // Step 1: DTO를 Entity로 매핑
        Template3Entity template3Entity = Template3Entity.builder()
                .description(template3DTO.getDescription())
                .type(template3DTO.getTemplateType())
                .imageNum(template3DTO.getImageNum())
                .imageCardEntityList(new ArrayList<>())
                .optionList(template3DTO.getOptions())
                .build();

        // Step 2: Template3Entity 저장
        Template3Entity savedTemplate3 = template3Repository.save(template3Entity);

        // Step 3: ImageCardDTO를 ImageCardEntity로 매핑하고 Template3Entity와 연관시키기
        for (Template3RequestDTO.GetImageCardDTO imageCardDTO : imageCardDTOList) {
            ImageCardEntity imageCardEntity = ImageCardEntity.builder()
                    .image(imageCardDTO.getImage()) // 이미지 URL 저장
                    .adjective(imageCardDTO.getAdjective())
                    .noun(imageCardDTO.getNoun())
                    .template3(savedTemplate3) // 저장된 Template3Entity와 연관시키기
                    .build();

            // 각 ImageCardEntity를 저장된 Template3Entity에 추가
            savedTemplate3.getImageCardEntityList().add(imageCardEntity);
        }

        // Step 4: ImageCardEntity 저장
        imageCardRepository.saveAll(savedTemplate3.getImageCardEntityList());

        return savedTemplate3;
    }
}


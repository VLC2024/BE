package com.vlc.maeummal.domain.template.template3.service;

import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import com.vlc.maeummal.domain.template.template3.dto.Template3RequestDTO;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template3.repository.ImageCardRepository;
import com.vlc.maeummal.domain.template.template3.repository.Template3Repository;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.UuidRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class Template3Service {

    private final Template3Repository template3Repository;
    private final ImageCardRepository imageCardRepository;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;

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
        template3Entity.setCreaterId(userAuthorizationConverter.getCurrentUserId());
        template3Entity.setTitle(template3DTO.getTitle());
        template3Entity.setLevel(template3DTO.getLevel());

        // Step 2: Template3Entity 저장
        Template3Entity savedTemplate3 = template3Repository.save(template3Entity);

        // Step 3: ImageCardDTO를 ImageCardEntity로 매핑하고 Template3Entity와 연관시키기
        for (Template3RequestDTO.GetImageCardDTO imageCardDTO : imageCardDTOList) {
            ImageCardEntity imageCardEntity = ImageCardEntity.builder()
                    .image(imageCardDTO.getImage()) // 이미지 URL 저장
                    .adjective(imageCardDTO.getAdjective())
                    .hint(imageCardDTO.getHint())
                    .noun(imageCardDTO.getNoun())
                    .template3(savedTemplate3) // 저장된 Template3Entity와 연관시키기
                    .build();
            log.info("imageCard hint : " + imageCardDTO.getHint());

            // 각 ImageCardEntity를 저장된 Template3Entity에 추가
            savedTemplate3.getImageCardEntityList().add(imageCardEntity);
            log.info("imageCard hint : " + imageCardDTO.getHint());
        }


        // Step 4: ImageCardEntity 저장
        imageCardRepository.saveAll(savedTemplate3.getImageCardEntityList());

        return savedTemplate3;
    }
    @Transactional
    public Template3Entity updateTemplate3WithImageCards(Long template3Id, Template3RequestDTO.GetTemplate3DTO template3DTO,
                                                         List<Template3RequestDTO.GetImageCardDTO> imageCardDTOList) {
        // Find the existing template
        Template3Entity template3Entity = template3Repository.findById(template3Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));
        //exception : not creater
        if(userAuthorizationConverter.getCurrentUserId() != template3Entity.getCreaterId()) {
            new AccessDeniedException("자신의 템플릿 수업만 수정할 수 있습니다.");

        }
        // Step 3: Update the existing template fields
        template3Entity.setDescription(template3DTO.getDescription());
        template3Entity.setType(template3DTO.getTemplateType());
        template3Entity.setImageNum(template3DTO.getImageNum());
        template3Entity.setOptionList(template3DTO.getOptions());
        template3Entity.setTitle(template3DTO.getTitle());
        template3Entity.setLevel(template3DTO.getLevel());

        // Step 4: Clear and update the image card list
        template3Entity.getImageCardEntityList().clear();

        for (Template3RequestDTO.GetImageCardDTO imageCardDTO : imageCardDTOList) {
            ImageCardEntity imageCardEntity = ImageCardEntity.builder()
                    .image(imageCardDTO.getImage()) // 이미지 URL 저장
                    .adjective(imageCardDTO.getAdjective())
                    .hint(imageCardDTO.getHint())
                    .noun(imageCardDTO.getNoun())
                    .template3(template3Entity) // 기존 Template3Entity와 연관시키기
                    .build();
            template3Entity.getImageCardEntityList().add(imageCardEntity);
        }

        // Step 5: Save the updated template with its image cards (Cascade save)
        Template3Entity updatedTemplate3 = template3Repository.save(template3Entity);

        return updatedTemplate3;

    }
    @Transactional
    public void deleteTemplate3(Long template3Id) {
        // Find the existing template
        Template3Entity template3Entity = template3Repository.findById(template3Id)
                .orElseThrow(() -> new EntityNotFoundException("템플릿을 찾을 수 없습니다."));

        //Exception: not creator
        if (!userAuthorizationConverter.getCurrentUserId().equals(template3Entity.getCreaterId())) {
            throw new org.springframework.security.access.AccessDeniedException("자신의 템플릿만 삭제할 수 있습니다.");
        }

        // Delete the associated story cards
        imageCardRepository.deleteAll(template3Entity.getImageCardEntityList());

        // Delete the template itself
        template3Repository.delete(template3Entity);
    }


}


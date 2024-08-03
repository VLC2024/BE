package com.vlc.maeummal.domain.word.service;

import com.vlc.maeummal.domain.word.dto.WordSetRequestDTO;
import com.vlc.maeummal.domain.word.dto.WordSetResponseDTO;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.domain.word.repository.WordSetRepository;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.Uuid;
import com.vlc.maeummal.global.aws.UuidRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WordService {
    private final WordSetRepository wordSetRepository;

    private final AmazonS3Manager s3Manager;

    private final UuidRepository uuidRepository;

    private final WordRepository wordRepository;

    // id -> wordset반환
    public WordSetResponseDTO.GetWordSetDTO getWordSet(Long setId) {
        // db에서 가져옴, dto변환
        WordSetResponseDTO.GetWordSetDTO wordSetResponseDTO = WordSetResponseDTO.GetWordSetDTO.getWordSetDTO(wordSetRepository.findById(setId).get());

        // return
        return wordSetResponseDTO;

    }
    public List<WordSetResponseDTO.GetWordSetDTO> getAllWordSet() {
        // db에서 가져옴, dto변환
        List<WordSetEntity> wordSetDTOList = wordSetRepository.findAll();
        return wordSetDTOList.stream().map(wordSet -> WordSetResponseDTO.GetWordSetDTO.getWordSetDTO(wordSet)).collect(Collectors.toList());

    }

    // 1. word meaning 가져온다.
    // 2. 이미지를 생성한다.
    // 2. 이미지를 s3에 저장
    // 3. 저장된 s3 url을 반환한다.
    public String saveWordImageInS3AndGetUrl_Test() throws IOException {
        // filepath 정해줌 -> 나중에 실제 경로로 대체
        File file = new File(new File("").getAbsolutePath() + "/src/main/resources/static/images/catss.PNG");
        // Convert local file to MultipartFile
//        MultipartFile multipartFile = convertFile(filePath);

        // Generate UUID
        String uuid = UUID.randomUUID().toString();

        // Save UUID in database
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid)
                .build());

        // Upload file to S3 and get URL
        String imageUrl = s3Manager.uploadFile_for_test(s3Manager.generateWordKeyName(savedUuid), file);

        // Return the URL
        return imageUrl;
    }


    // WordSetRequestDTO.GetWordSetDTO wordSetDTO, List<WordSetRequestDTO.GetWordDTO> WordDTOList
    @Transactional
    public WordSetEntity saveWordSetWithWords(WordSetRequestDTO.GetWordSetDTO wordSetDTO, List<WordSetRequestDTO.GetWordDTO> wordDTOList) {
        // Step 1: Map DTO to EntitywordSetDT
        WordSetEntity wordSetEntity = WordSetEntity.builder()
                .title(wordSetDTO.getTitle())
                .description(wordSetDTO.getDescription())
                .category(wordSetDTO.getCategory())
                .wordEntities(new ArrayList<>())
                .build();
        // Step 2: Save WordSetEntity
        WordSetEntity savedWordSet = wordSetRepository.save(wordSetEntity);

        // Step 3: Map WordDTOs to WordEntities and associate with WordSetEntity
        for (WordSetRequestDTO.GetWordDTO wordDTO : wordDTOList) {
            WordEntity wordEntity = WordEntity.builder()
                    .meaning(wordDTO.getMeaning())
                    .image(wordDTO.getImage()) // image url 저장
                    .prompt(wordDTO.getPrompt())
                    .description(wordDTO.getDescription())
                    .wordSet(savedWordSet) // Associate with the saved WordSetEntity
                    .build();

            // Add each WordEntity to the saved WordSetEntity
            savedWordSet.getWordEntities().add(wordEntity);
            // Step 4: Save WordEntities
            wordSetRepository.save(savedWordSet);
        }
            return savedWordSet;
        }

    public List<WordSetEntity> getAllWordSetFromTitleContaining(String title){
        return wordSetRepository.findByTitleContaining(title);
    }

}

package com.vlc.maeummal.domain.prep.prep1.controller;

import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.prep.prep1.dto.Prep1DTO;
import com.vlc.maeummal.domain.prep.prep1.service.OpenAIService;
import com.vlc.maeummal.domain.prep.prep1.service.Prep1Mapper;
import com.vlc.maeummal.domain.prep.prep1.service.QuizService;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.Uuid;
import com.vlc.maeummal.global.aws.UuidRepository;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.enums.MissionType;
import com.vlc.maeummal.global.openAi.Base64DecodedMultipartFile;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/prep1")
public class ImageTextController {

    private OpenAIService openAIService;
    private AiService aiService;
    private QuizService quizService;
    private Prep1Mapper prep1Mapper; // Prep1Mapper 필드 추가
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;
    private ChallengeService challengeService;

    private UserAuthorizationConverter userAuthorizationConverter;

    public ImageTextController(OpenAIService openAIService, AiService aiService, QuizService quizService, Prep1Mapper prep1Mapper, AmazonS3Manager s3Manager, UuidRepository uuidRepository) {
        this.openAIService = openAIService;
        this.aiService = aiService;
        this.quizService = quizService;
        this.prep1Mapper = prep1Mapper;
        this.s3Manager = s3Manager;
        this.uuidRepository = uuidRepository;
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateContent(@RequestParam String category) {
        String category_en = URLEncoder.encode(category, StandardCharsets.UTF_8);

        String generatedText = openAIService.generateText("Write one sentence about this: " + category_en);
//        String imageUrl = aiService.generatePicture(generatedText);
        String base64ImageData = aiService.generatePicture(generatedText); // base64 data
        String imageUrl = null;
        //  Base64 데이터를 MultipartFile로 변환하여 S3에 업로드
        try {
            imageUrl = uploadImageToS3(base64ImageData);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println( "Failed to upload image");

        }
        System.out.println( "Image uploaded successfully");

        // 문장 나누기
        String[] parts = generatedText.split(" ", 2);
        String firstPart = parts[0];
        String secondPart = parts[1];

        // 보기 생성
        List<String> options = quizService.generateOptions(secondPart);

        // Prep1DTO 생성
        Prep1DTO prep1DTO = new Prep1DTO();
        prep1DTO.setSituation(category);
        prep1DTO.setDetailedSituation(generatedText);
        prep1DTO.setAnswer(secondPart);
        prep1DTO.setExplanation("");  // 설명이 필요하다면 생성하거나 빈 값으로 설정
        prep1DTO.setUid(prep1DTO.getUid());  // UID는 실제 사용자의 식별자로 설정

        // 옵션 설정
        if (options.size() > 0) prep1DTO.setOption1(options.get(0));
        if (options.size() > 1) prep1DTO.setOption2(options.get(1));
        if (options.size() > 2) prep1DTO.setOption3(options.get(2));
        if (options.size() > 3) prep1DTO.setOption4(options.get(3));

        prep1DTO.setImageUrl(imageUrl);

        // 데이터 저장
        Prep1DTO savedPrep1DTO = quizService.savePrep1(prep1DTO);

        // 결과 반환
        Map<String, Object> response = new HashMap<>();
        response.put("imageUrl", imageUrl);
        response.put("firstPart", firstPart);
        response.put("options", options);
        response.put("savedData", savedPrep1DTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-answer")
    public ResponseEntity<?> checkAnswer(@RequestBody Map<String, Object> payload) {
        int questionId = (Integer) payload.get("questionId");
        String selectedOption = (String) payload.get("selectedOption");

        boolean isCorrect = quizService.checkAnswer(questionId, selectedOption);
        challengeService.completeMission(userAuthorizationConverter.getCurrentUserId(), MissionType.PREP);

        Map<String, Object> response = new HashMap<>();
        response.put("isCorrect", isCorrect);
        return ResponseEntity.ok(response);
    }

    public String uploadImageToS3(String base64ImageData) throws IOException {
        // Base64 데이터 디코딩
        byte[] imageBytes = Base64.decodeBase64(base64ImageData);

        // 바이트 배열을 사용하여 MultipartFile 객체 생성
        // Base64DecodedMultipartFile 객체 생성
        MultipartFile multipartFile = new Base64DecodedMultipartFile(imageBytes, "data:image/png;base64");

        // Generate UUID
        String uuid = UUID.randomUUID().toString();

        // Save UUID in database
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid)
                .build());

        // Upload file to S3 and get URL
        String dirname = s3Manager.generateWordKeyName(savedUuid);
        String imageUrl = upload(multipartFile, dirname);

        // Return the URL
        return imageUrl;
        // S3에 업로드 (s3Uploader는 S3에 파일을 업로드하는 서비스)

    }

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        //removeNewFile(uploadFile);
//        return s3Manager.uploadFile_for_test(dirName, uploadFile);

        return upload(uploadFile, dirName);
    }
    private String upload(File uploadFile, String dirName) {
        String uploadImageUrl = s3Manager.uploadFile_for_test(dirName, uploadFile);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
//            log.info("File delete success");
            return;
        }
//        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}

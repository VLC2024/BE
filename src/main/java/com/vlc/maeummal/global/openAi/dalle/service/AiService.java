package com.vlc.maeummal.global.openAi.dalle.service;

import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.Uuid;
import com.vlc.maeummal.global.aws.UuidRepository;
import com.vlc.maeummal.global.openAi.Base64DecodedMultipartFile;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiService {

    @Resource(name = "")
    private final OpenAiService openAiService;
    private final AmazonS3Manager s3Manager;
    private final UuidRepository uuidRepository;

    public String generatePicture(String prompt) {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt(prompt)
                .size("512x512")
                .n(1)
                .responseFormat("b64_json") // multipartfile로 변한하기 위해
                .build();

        // 이미지 생성 API 호출 및 Base64 데이터 추출
        String base64ImageData = openAiService.createImage(createImageRequest).getData().get(0).getB64Json();
        return base64ImageData;
    }
    public String uploadImageToS3(String base64ImageData) throws IOException {
        // Base64 데이터 디코딩
        byte[] imageBytes = Base64.decodeBase64(base64ImageData);

        // 바이트 배열을 사용하여 MultipartFile 객체 생성
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
    /**
     * MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
     * get : 파일, dirname
     * return : url
     *
     * */
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return upload(uploadFile, dirName);
    }
    // S3로 파일 업로드하기
    /**
     * 변환된 file을 s3에 저장 & 로컬에 생성된 이미지 제거
     * get : 이미지 파일, dirname
     * return : 저장된 s3 url
     *
     * */

    private String upload(File uploadFile, String dirName) {
        String uploadImageUrl = s3Manager.uploadFile_for_test(dirName, uploadFile);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    /**
     * Multipart file -> File로 변환. why? multipartfile로 올라갈때 이상한 값이 저장됨.
     * get: Multipartfile
     * return : 이미지 File
     * */
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
    // 로컬에 저장된 이미지 지우기
    /**
     * MultipartFile -> File 변환시 로컬에 이미지 가 생겨서 지움.
     * */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
//            log.info("File delete success");
            return;
        }
//        log.info("File delete fail");
    }














}
package com.vlc.maeummal.global.aws.service;

import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.aws.Uuid;
import com.vlc.maeummal.global.aws.UuidRepository;
import com.vlc.maeummal.global.openAi.Base64DecodedMultipartFile;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
@Service
public class S3Service {
    @Autowired
    private UuidRepository uuidRepository;
    @Autowired
    private AmazonS3Manager s3Manager;
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

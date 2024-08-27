package com.vlc.maeummal.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vlc.maeummal.global.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;


    public String uploadFileWithoutImg(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
        }

        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }
    public String uploadMultipartFile(String dirName, MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "빈 파일은 업로드할 수 없습니다.");
        }

        String uploadFileUrl = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        objectMetadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            String key = dirName + "/" + UUID.randomUUID() + "." + file.getOriginalFilename();
            amazonS3.putObject(new PutObjectRequest(bucket, key, inputStream, objectMetadata));
            uploadFileUrl = amazonS3.getUrl(bucket, key).toString();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
        }

        return uploadFileUrl;
    }
    public String uploadFile_for_test(String keyName, File file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.length());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file).withMetadata(metadata));
        } catch (Exception e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new RuntimeException("Error uploading file to S3", e);
        }
        return amazonS3.getUrl(amazonConfig.getBucket(), keyName).toString();
    }

    public void deleteFile(String keyName) {
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(amazonConfig.getBucket(), keyName));
        } catch (Exception e) {
            log.error("error at AmazonS3Manager deleteFile: {}", (Object) e.getStackTrace());
        }
    }
    public String generateWordKeyName(Uuid uuid) {
        return amazonConfig.getWordPath() + '/' + uuid.getUuid();
    }

    // URL에서 S3 key를 추출하는 메서드
    public String extractKeyFromUrl(String imageUrl) {
        // 버킷의 URL 접두사 추출
        String bucketUrlPrefix = amazonS3.getUrl(amazonConfig.getBucket(), "").toString();

        // 접두사 뒤의 슬래시('/') 유무를 고려하여 정확히 key를 추출
        if (imageUrl.startsWith(bucketUrlPrefix)) {
            return imageUrl.substring(bucketUrlPrefix.length());
        } else {
            throw new IllegalArgumentException("Invalid S3 URL: " + imageUrl);
        }
    }


//
//    public String generateReviewKeyName(Uuid uuid) {
//        return amazonConfig.getReviewPath() + '/' + uuid.getUuid();
//    }
//
//    public String generateMatchingKeyName(Uuid uuid) {
//        return amazonConfig.getMatchingPath() + '/' + uuid.getUuid();
//    }
//
//    public String generateMemberKeyName(Uuid uuid) {
//        return amazonConfig.getMemberPath() + '/' + uuid.getUuid();
//    }
//
//    public String generateChatKeyName(Uuid uuid) {
//        return amazonConfig.getChatPath() + '/' + uuid.getUuid();
//    }
//
//    public String generatePostKeyName(Uuid uuid) {
//        return amazonConfig.getPostPath() + '/' + uuid.getUuid();
//    }
}


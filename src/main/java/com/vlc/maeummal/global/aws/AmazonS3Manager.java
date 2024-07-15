package com.vlc.maeummal.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vlc.maeummal.global.config.AmazonConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;


    public String uploadFile(String keyName, MultipartFile file){
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        try {
            amazonS3.putObject(new PutObjectRequest(amazonConfig.getBucket(), keyName, file.getInputStream(), metadata));
        }catch (IOException e){
            log.error("error at AmazonS3Manager uploadFile : {}", (Object) e.getStackTrace());
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


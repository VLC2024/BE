package com.vlc.maeummal.domain.member.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.vlc.maeummal.domain.member.dto.StudentDTO;
import com.vlc.maeummal.domain.member.dto.TeacherDTO;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import com.vlc.maeummal.global.aws.AmazonS3Manager;
import com.vlc.maeummal.global.converter.UserAuthorizationConverter;
import com.vlc.maeummal.global.openAi.dalle.service.AiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {
    private final MemberRepository memberRepository;
    private final MemberReposirotyUsingId memberReposirotyUsingId;
    private final UserAuthorizationConverter userAuthorizationConverter;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Manager amazonS3Manager;


    public StudentDTO.GetStudentInfo getStudentInfo(MemberEntity member){
        return StudentDTO.GetStudentInfo.getStudentInfo(member);
    }

    public TeacherDTO.GetTeacherInfo getTeacherInfo(MemberEntity member){
        return TeacherDTO.GetTeacherInfo.getTeacherInfo(member);
    }

    @Transactional
    public StudentDTO.GetStudentInfo updateStudentInfo(MemberEntity member, StudentDTO.GetStudentInfo studentInfo){
        // 학생 정보 업데이트
        member.setImage(studentInfo.getProfileImage());
        member.setName(studentInfo.getName());
        member.setEmail(studentInfo.getEmail());
        member.setPhoneNumber(studentInfo.getPhoneNum());
        member.setIq(studentInfo.getIq());

        memberRepository.save(member);

        return StudentDTO.GetStudentInfo.getStudentInfo(member);
    }
    @Transactional
    public TeacherDTO.GetTeacherInfo updateTeacherInfo(MemberEntity member, TeacherDTO.GetTeacherInfo teacherInfo){

        member.setName(teacherInfo.getName());
        member.setEmail(teacherInfo.getEmail());
        member.setPhoneNumber(teacherInfo.getPhoneNum());

        memberRepository.save(member);

        return TeacherDTO.GetTeacherInfo.getTeacherInfo(member);
    }

    @Transactional
    public void uploadProfileImage(MultipartFile file) throws IOException {
        Long memberId = userAuthorizationConverter.getCurrentUserId();
        MemberEntity member = memberReposirotyUsingId.findById(memberId)
                .orElseThrow(() -> new RuntimeException("잘못된 접근입니다. 회원가입 먼저 진행해주세요."));

        // Todo : 기존 프로필 이미지 삭제
        /*// 기존 프로필 이미지 삭제 로직
        String currentImageUrl = member.getImage();
        if (currentImageUrl != null && !currentImageUrl.isEmpty()) {
            String currentKeyName = amazonS3Manager.extractKeyFromUrl(currentImageUrl);
            amazonS3Manager.deleteFile(currentKeyName);
        }*/

        // 새로운 이미지 업로드
        String imageUrl = amazonS3Manager.uploadMultipartFile("users", file);

        // 멤버 엔티티 업데이트 및 저장
        member.setImage(imageUrl);
        memberRepository.save(member);
    }

    @Transactional
    public void changePassword(String currentPassword, String newPassword) {
        Long memberId = userAuthorizationConverter.getCurrentUserId();
        MemberEntity member = memberReposirotyUsingId.findById(memberId)
                .orElseThrow(() -> new RuntimeException("잘못된 접근입니다. 회원가입 먼저 진행해주세요."));

        if (!passwordEncoder.matches(currentPassword, member.getPassword())) {
            throw new RuntimeException("비밀번호가 틀립니다. 다시 입력해주세요.");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        memberRepository.save(member);
    }
}

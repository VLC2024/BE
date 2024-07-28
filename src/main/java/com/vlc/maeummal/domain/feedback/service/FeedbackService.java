package com.vlc.maeummal.domain.feedback.service;

import com.vlc.maeummal.global.common.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeedbackService extends BaseEntity {

    /**
     * 1. 데이터 가져오기
     * - 템플릿 이미지,정답 set
     * - 학생이 작성한 정답 set
     *
     * 2. feedbackcard 에 각각 저장하기
     *
     * 3. feedback 에 두개 리스트로 나눠서 넣기
     *
     * TOdo. ai 피드백 생성해서 저장하기
     *
     *
     * */
}

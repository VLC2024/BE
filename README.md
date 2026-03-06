
## 프로젝트 개요

마음말은 학생이 제시된 문제(단어·이미지·스토리 등)에 대해 답을 작성하면, 정답과 비교하여 AI가 개인 맞춤형 학습 피드백을 생성하는 감정·언어 학습 서비스입니다.

기존 학습 서비스는 정답 여부만 제공하는 경우가 많지만,
마음말은 LLM을 활용해 학생의 답변을 분석하고 개선 방향을 제시하는 학습 피드백 시스템을 목표로 개발되었습니다.

문제 템플릿 기반 채점 시스템 설계
정답 데이터와 학생 답변 비교 로직 구현
AI 기반 학습 피드백 생성 기능 개발
피드백 데이터 모델링 및 API 구현

## 시스템 설계
Template 기반 문제 처리 구조

문제 유형이 단어, 이미지, 스토리 등 다양한 형태로 존재했기 때문에
모든 문제를 하나의 구조로 처리하기 어렵다는 문제가 있었습니다.

이를 해결하기 위해 TemplateType 기반 문제 처리 구조를 설계했습니다.

### 문제 유형

TEMPLATE1 : 단어 의미 매칭
TEMPLATE2 : 스토리 순서 배열
TEMPLATE3 : 이미지 감정 표현
TEMPLATE4 : 스토리 기반 문제
TEMPLATE5 : 단어 학습 문제

학생 답안 제출 시 TemplateType을 기준으로 해당 로직을 실행하도록 설계했습니다

` 
switch (type) {
    case TEMPLATE1:
        return processTemplate1ToFeedback(studentAnswerDTO);
    case TEMPLATE2:
        return processTemplate2ToFeedback(studentAnswerDTO);
} 
`


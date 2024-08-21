package com.vlc.maeummal.domain.feedback.service;

import com.vlc.maeummal.domain.challenge.service.ChallengeService;
import com.vlc.maeummal.domain.feedback.dto.FeedbackRequestDTO;
import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.feedback.repository.FeedbackRepository;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.template.common.TemplateEntity;
import com.vlc.maeummal.domain.template.template1.entity.Template1Entity;
import com.vlc.maeummal.domain.template.template1.repository.Template1Repository;
import com.vlc.maeummal.domain.template.common.StoryCardEntity;
import com.vlc.maeummal.domain.template.template2.entity.Template2Entity;
import com.vlc.maeummal.domain.template.template2.repository.Template2Repository;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template3.repository.Template3Repository;
import com.vlc.maeummal.domain.template.template4.entity.Template4Entity;
import com.vlc.maeummal.domain.template.template4.repository.Template4Repository;
import com.vlc.maeummal.domain.template.template5.entity.Template5Entity;
import com.vlc.maeummal.domain.template.template5.entity.WordCardEntity;
import com.vlc.maeummal.domain.template.template5.repository.Template5Repository;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.common.BaseEntity;
import com.vlc.maeummal.global.enums.MissionType;
import com.vlc.maeummal.global.enums.TemplateType;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedbackService extends BaseEntity {

    final Template3Repository template3Repository;
    final Template4Repository template4Repository;
    final Template1Repository template1Repository;
    final Template2Repository template2Repository;
    final MemberReposirotyUsingId memberRepository;
    final FeedbackRepository feedbackRepository;
    final Template5Repository template5Repository;
    final ChallengeService challengeService;
//    final TemplateRepository<TemplateEntity> templateRepository;


    /**
     * 1. 데이터 가져오기
     * - 템플릿 이미지, 정답 set
     * - 학생이 작성한 정답 set
     *
     * 2. feedbackcard 에 각각 저장하기
     *
     * 3. feedback 에 두 개 리스트로 나눠서 넣기
     *
     * Todo: AI 피드백 생성해서 저장하기
     */
    // 8.5 추가
/**
 * 특정 피드백 찾기
 * 모든 템플릿 사용 가능
 * */
//    public FeedbackResponseDTO.GetFeedbackDetailDTO getFeedbackDetail(Long id){
//        FeedbackEntity feedbackEntity = feedbackRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("FeedbackDetail not found with id: " + id));
//        return FeedbackResponseDTO.GetFeedbackDetailDTO.convertToFeedbackDetail(feedbackEntity);
//
//
//    }
public FeedbackResponseDTO.GetFeedbackDetailDTO getFeedbackDetail(Long feedbackId) {
    FeedbackEntity feedbackEntity = feedbackRepository.findById(feedbackId)
            .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));

    // 추가적인 검증이 필요한 경우
    if (feedbackEntity.getStudent() == null) {
        // 로깅하거나 적절한 처리를 수행할 수 있습니다.
        throw new IllegalStateException("Student information is missing in feedback");
    }

    return FeedbackResponseDTO.GetFeedbackDetailDTO.convertToFeedbackDetail(feedbackEntity);
}

    /**
     * 모든 피드백 리스트 가져오기
     * 모든 템플릿 사용 가능
     * */
    public List<FeedbackResponseDTO.GetFeedbackDTO> getAllFeedback() {
        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAll();
        return feedbackEntities.stream()
                .map(this::mapToGetFeedbackDTO)
                .collect(Collectors.toList());
    }
    /**
     * 피드백을 제목과 매칭
     * 템플릿 3만 사용가능
     * 특이사항 : 제목 으로 나중에 수정해야함.
     *
     * */


    private FeedbackResponseDTO.GetFeedbackDTO mapToGetFeedbackDTO(FeedbackEntity feedbackEntity) {
        // 제목을 templateId로 조회
        String title = template3Repository.findById(feedbackEntity.getTemplateId())
                .map(Template3Entity::getDescription)// TOdo 제목으로 수정해야함(lesson생성 후)
                .orElse("Unknown");

        // DTO로 변환
        return FeedbackResponseDTO.GetFeedbackDTO.getFeedback(feedbackEntity, title);
    }
    /**
     * 학생별 피드백 리스트 가져오기
     * 모든 템플릿 사용 가능
     * */

    public List<FeedbackResponseDTO.GetFeedbackDetailDTO> getAllFeedbackFromStudent(Long studentId) {
        // MemberEntity가 존재하지 않을 경우 예외를 발생시킵니다.
        MemberEntity member = memberRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + studentId));

        // 학생에 대한 피드백을 모두 가져옵니다.
        List<FeedbackEntity> feedbackList = feedbackRepository.findAllByStudent(member);
        return feedbackList.stream()
                .map(FeedbackResponseDTO.GetFeedbackDetailDTO::convertToFeedbackDetail)
                .collect(Collectors.toList());

    }
    /**
     * Controller에서 호출되는 메인 메소드
     *
     * */
    public FeedbackResponseDTO.GetFeedbackDetailDTO setFeedbackFromAnswer(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        Long templateId = studentAnswerDTO.getTemplateId();
        TemplateType type = studentAnswerDTO.getTemplateType();

        Long memberId = memberRepository.findById(studentAnswerDTO.getStudentId())
                .map(MemberEntity::getMemberId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + studentAnswerDTO.getStudentId()));

        challengeService.completeMission(memberId, MissionType.TEMP);

        if (isValidate(templateId, type)) {
            switch (type) {
//                case TEMPLATE1: // TODO 반환값 설정
//                    FeedbackEntity feedbackEntity1 = processTemplate1ToFeedback(studentAnswerDTO);
//                    // Return null or an appropriate default value if TEMPLATE1 does not need to return an ID
//                    return feedbackEntity1 != null ? feedbackEntity1.getId() : null;
                case TEMPLATE2: // TODO 반환값 설정
                    FeedbackEntity feedbackEntity2 = processTemplate2ToFeedback(studentAnswerDTO);
                    return getFeedbackDetail(feedbackEntity2.getId());
                case TEMPLATE3: // TODO 반환값 설정
                    FeedbackEntity feedbackEntity3 = processTemplate3ToFeedback(studentAnswerDTO);
                    return getFeedbackDetail(feedbackEntity3.getId());
                case TEMPLATE4: // TODO 반환값 설정
                    FeedbackEntity feedbackEntity4 =processTemplate4ToFeedback(studentAnswerDTO);
                    return getFeedbackDetail(feedbackEntity4.getId());
                case TEMPLATE5: // TODO 반환값 설정
                    FeedbackEntity feedbackEntity5 =processTemplate5ToFeedback(studentAnswerDTO);
                    return getFeedbackDetail(feedbackEntity5.getId());
                default:
                    throw new IllegalArgumentException("Unknown template type: " + studentAnswerDTO.getTemplateType());
            }
        } else {
            throw new IllegalArgumentException("Template id not found with id: " + templateId);
        }
    }

//    public void setFeedbackFromAnswer(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
//        Long templateId = studentAnswerDTO.getTemplateId();
//        TemplateType type = studentAnswerDTO.getTemplateType();
//
//        Long memberId = memberRepository.findById(studentAnswerDTO.getStudentId())
//                .map(MemberEntity::getMemberId) // MemberEntity가 존재하면 MemberId를 반환합니다
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + studentAnswerDTO.getTemplateId()));
//        log.info("memberId" + memberId + "   ");
//        challengeService.completeMission(memberId, MissionType.TEMP);
//
//        if (isValidate(templateId, type)) {
//            switchForTemplateType(studentAnswerDTO, studentAnswerDTO.getTemplateType());
//
//        } else {
//            throw new IllegalArgumentException("Template id not found with id: " + templateId);
//        }
//    }

//    public void switchForTemplateType(FeedbackRequestDTO.GetAnswer studentAnswerDTO, TemplateType templateType) {
//        // 템플릿 타입에 따라 적절한 리포지토리와 함수 선택
//
//        switch (templateType) {
//            case TEMPLATE1:
//                processTemplate1ToFeedback(
//                        studentAnswerDTO
//
//                );
//                break;
//            case TEMPLATE2:
//                processTemplate2ToFeedback(
//                        studentAnswerDTO
//
//                );
//                break;
//            case TEMPLATE3:
//                processTemplate3ToFeedback(
//                        studentAnswerDTO
//
//                );
//                break;
//            case TEMPLATE4:
//                processTemplate4ToFeedback(
//                        studentAnswerDTO
//
//                );
//                break;
//            case TEMPLATE5:
//                processTemplate5ToFeedback(
//                        studentAnswerDTO
//
//                );
//                break;
//            default:
//                throw new IllegalArgumentException("Unknown template type: " + studentAnswerDTO.getTemplateType());
//        }
//    }
    /**
     * 학생의 답 & 정답 으로 피드백 만드는 함수 호출
     * 재료 : 학생의 답 & 이미지 카드 s
     * 조건 : 각 템플릿 마다 사용되는 리스트
     * */
    public void processTemplate1ToFeedback(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        Template1Entity template1 = template1Repository.findById(studentAnswerDTO.getTemplateId()).get();
        List<WordEntity> wordEntities = template1.getWordEntities();
        log.info("in processTemplate1ToFeedback: 1");
        // Todo 기본 정보 설정 - 공통
        FeedbackEntity feedbackEntity = setFeedbackEntityWithoutList(template1, studentAnswerDTO);
        // Todo 정답 리스트 & 학생 답 리스트 포맷
        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntitiesFromWords(wordEntities));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntityFromWords(wordEntities, studentAnswerDTO));

        log.info("in processTemplate1ToFeedback: 2");
        feedbackRepository.save(feedbackEntity);

    }
    public FeedbackEntity processTemplate2ToFeedback(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        // Todo 기본 정보 설정 - 공통
        Template2Entity template2 = template2Repository.findById(studentAnswerDTO.getTemplateId()).get();
        List<StoryCardEntity> storyCardEntityList = template2.getStoryCardEntityList();
        FeedbackEntity feedbackEntity = setFeedbackEntityWithoutList(template2, studentAnswerDTO);

        // Todo 정답 리스트 & 학생 답 리스트 포맷
        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntityFromStoryCards(storyCardEntityList));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntityFromStoryCards(storyCardEntityList, studentAnswerDTO));

        // Todo 반환
        log.info("in processTemplate2ToFeedback: ");
        FeedbackEntity feedback = feedbackRepository.save(feedbackEntity);
        return feedback;
    }
    public FeedbackEntity processTemplate3ToFeedback(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        Template3Entity template3 = template3Repository.findById(studentAnswerDTO.getTemplateId()).get();
        List<ImageCardEntity> imageCardEntities = template3.getImageCardEntityList();
        log.info("in processTemplate3ToFeedback: 1");
        // Todo 기본 정보 설정 - 공통
        FeedbackEntity feedbackEntity = setFeedbackEntityWithoutList(template3, studentAnswerDTO);
        // TOdo 각자 수행.
        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntityFromImageCards(imageCardEntities));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntityFromImageCards(imageCardEntities, studentAnswerDTO));
        // Todo 반환
        log.info("in processTemplate3ToFeedback: 2");
        FeedbackEntity feedback = feedbackRepository.save(feedbackEntity);
        return feedback;

    }
    public FeedbackEntity processTemplate4ToFeedback(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        // Todo 기본 정보 설정 - 공통
        Template4Entity template4 = template4Repository.findById(studentAnswerDTO.getTemplateId()).get();

        List<StoryCardEntity> storyCardEntityList = template4.getStoryCardEntityList();
        FeedbackEntity feedbackEntity = setFeedbackEntityWithoutList(template4, studentAnswerDTO);

        // Todo 정답 리스트 & 학생 답 리스트 포맷
        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntityFromStoryCards(storyCardEntityList));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntityFromStoryCards(storyCardEntityList, studentAnswerDTO));
        // Todo 반환
        log.info("in processTemplate4ToFeedback: ");
        FeedbackEntity feedback = feedbackRepository.save(feedbackEntity);
        return feedback;

    }
    public FeedbackEntity processTemplate5ToFeedback(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        Template5Entity template5 = template5Repository.findById(studentAnswerDTO.getTemplateId()).get();
        List<WordCardEntity> wordCardEntities = template5.getWordListEntities();
        log.info("in processTemplate3ToFeedback: 1");
        // Todo 기본 정보 설정 - 공통
        FeedbackEntity feedbackEntity = setFeedbackEntityWithoutList(template5, studentAnswerDTO);
        // Todo 정답 리스트 & 학생 답 리스트 포맷
        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntitiesFromWordCards(wordCardEntities));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntityFromWordCards(wordCardEntities, studentAnswerDTO));
        // Todo 반환
        log.info("in processTemplate3ToFeedback: 2");
        FeedbackEntity feedback = feedbackRepository.save(feedbackEntity);
        return feedback;
    }


    // temp1에서 사용
    public List<FeedbackCardEntity> setFeedbackCardEntitiesFromWords(List<WordEntity> wordEntities) {
        List<FeedbackCardEntity> feedbackCardEntities = wordEntities.stream()
                .map(word -> {
                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(word.getImage());
                    feedbackCard.setMeaning(word.getMeaning());
                    feedbackCard.setDescription(word.getDescription());
                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return feedbackCardEntities;
    }
    public List<FeedbackCardEntity> setStudentFeedbackCardEntityFromWords(List<WordEntity> wordEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        if (wordEntities == null || studentAnswerDTO == null) {
            throw new IllegalArgumentException("Image card entities and student answer DTO cannot be null");
        }

        List<String> answerList = studentAnswerDTO.getAnswerList();
        if (answerList == null) {
            throw new IllegalArgumentException("Student answer list cannot be null");
        }
        if (answerList.size() != wordEntities.size()) {
            throw new IllegalArgumentException("Answer list size does not match the image card entities size");
        }

        List<FeedbackCardEntity> studentFeedbackCardEntities = IntStream.range(0, wordEntities.size())
                .mapToObj(index -> {
                    WordEntity word = wordEntities.get(index);

                    String studentMeaing = studentAnswerDTO.getAnswerList().size() > index
                            ? studentAnswerDTO.getAnswerList().get(index)
                            : null;

                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(word.getImage());
                    feedbackCard.setMeaning(studentMeaing);
                    feedbackCard.setDescription(word.getDescription());

                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return studentFeedbackCardEntities;
    }


/**
 * 피드백 entity를 직접 만드는 역할
 * 재료 : 학생의 답 & 이미지 카드 s
 * 조건 : 템플릿 별 사용하는 리스트 필요
 * */
    public FeedbackEntity setFeedbackEntityWithoutList(TemplateEntity template, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .templateId(template.getId())
                .aiFeedback("template ai feedback....")
                .templateType(template.getType())
                .imageNum(template.getImageNum())
                .templateType(studentAnswerDTO.getTemplateType())
                .solution(template.getDescription())
                .student(memberRepository.findById(studentAnswerDTO.getStudentId())
                        .orElse(null))
                .teacher(memberRepository.findById(studentAnswerDTO.getStudentId())
                        .orElse(null)) // Todo: 교사로 수정해야 함
                .build();
        return feedbackEntity;
    }
    /**
     * 학생 답 리스트를 FeedbackCard로 포맷
     * who? 이미지-동사-명사 형태의 값을 가지고 있는 템플릿 사용 권장
     * */
    public List<FeedbackCardEntity> setStudentFeedbackCardEntityFromImageCards(List<ImageCardEntity> imageCardEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        if (imageCardEntities == null || studentAnswerDTO == null) {
            throw new IllegalArgumentException("Image card entities and student answer DTO cannot be null");
        }

        List<String> answerList = studentAnswerDTO.getAnswerList();
        if (answerList == null) {
            throw new IllegalArgumentException("Student answer list cannot be null");
        }
        if (answerList.size() != imageCardEntities.size()) {
            throw new IllegalArgumentException("Answer list size does not match the image card entities size");
        }

        List<FeedbackCardEntity> studentFeedbackCardEntities = IntStream.range(0, imageCardEntities.size())
                .mapToObj(index -> {
                    ImageCardEntity imageCard = imageCardEntities.get(index);
                    String studentAdjective = studentAnswerDTO.getAnswerList().size() > index
                            ? studentAnswerDTO.getAnswerList().get(index)
                            : null;

                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(imageCard.getImage());
                    feedbackCard.setAdjective(studentAdjective);
                    feedbackCard.setNoun(imageCard.getNoun());

                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return studentFeedbackCardEntities;
    }
    /**
     *
     * using by : temp2, temp4
     * */
    public List<FeedbackCardEntity> setStudentFeedbackCardEntityFromStoryCards(List<StoryCardEntity> storyCardEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        if (storyCardEntities == null || studentAnswerDTO == null) {
            throw new IllegalArgumentException("Image card entities and student answer DTO cannot be null");
        }

        List<String> answerList = studentAnswerDTO.getAnswerList();
        if (answerList == null) {
            throw new IllegalArgumentException("Student answer list cannot be null");
        }
        if (answerList.size() != storyCardEntities.size()) {
            throw new IllegalArgumentException("Answer list size does not match the image card entities size");
        }
        List<FeedbackCardEntity> studentFeedbackCardEntities = IntStream.range(0, storyCardEntities.size())
                .mapToObj(index -> {
                    StoryCardEntity storyCard = storyCardEntities.get(index);
                    Integer answerNumber = studentAnswerDTO.getAnswerList().size() > index
                            ? Integer.parseInt(studentAnswerDTO.getAnswerList().get(index))
                            : null;

                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(storyCard.getImage());
                    feedbackCard.setAnswerNumber(answerNumber);
                    feedbackCard.setDescription(storyCard.getDescription());

                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return studentFeedbackCardEntities;
    }
    /**
     * 정답 리스트를 FeedbackCard로 포맷
     * who? 이미지-동사-명사 형태의 값을 가지고 있는 템플릿 사용 권장
     * */
    public List<FeedbackCardEntity> setFeedbackCardEntityFromImageCards(List<ImageCardEntity> imageCardEntities) {
        List<FeedbackCardEntity> feedbackCardEntities = imageCardEntities.stream()
                .map(imageCard -> {
                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(imageCard.getImage());
                    feedbackCard.setAdjective(imageCard.getAdjective());
                    feedbackCard.setNoun(imageCard.getNoun());
                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return feedbackCardEntities;
    }
    /**
     *
     * using by : temp2, temp4
     * */
    public List<FeedbackCardEntity> setFeedbackCardEntityFromStoryCards(List<StoryCardEntity> storyCardEntities) {
        List<FeedbackCardEntity> feedbackCardEntities = storyCardEntities.stream()
                .map(storyCard -> {
                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(storyCard.getImage());
                    feedbackCard.setAnswerNumber(storyCard.getAnswerNumber());
                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return feedbackCardEntities;

    }

    /**
     * 학생 답 리스트를 FeedbackCard로 포맷
     * who? 이미지-동사-명사 형태의 값을 가지고 있는 템플릿 사용 권장
     * */
    public List<FeedbackCardEntity> setStudentFeedbackCardEntityFromWordCards(List<WordCardEntity> wordCardEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        if (wordCardEntities == null || studentAnswerDTO == null) {
            throw new IllegalArgumentException("Image card entities and student answer DTO cannot be null");
        }

        List<String> answerList = studentAnswerDTO.getAnswerList();
        if (answerList == null) {
            throw new IllegalArgumentException("Student answer list cannot be null");
        }
        if (answerList.size() != wordCardEntities.size()) {
            throw new IllegalArgumentException("Answer list size does not match the image card entities size");
        }

        List<FeedbackCardEntity> studentFeedbackCardEntities = IntStream.range(0, wordCardEntities.size())
                .mapToObj(index -> {
                    WordCardEntity wordCard = wordCardEntities.get(index);

                    String studentMeaing = studentAnswerDTO.getAnswerList().size() > index
                            ? studentAnswerDTO.getAnswerList().get(index)
                            : null;

                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(wordCard.getImage());
                    feedbackCard.setMeaning(studentMeaing);
                    feedbackCard.setDescription(wordCard.getDescription());

                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return studentFeedbackCardEntities;
    }
    /**
     * 정답 리스트를 FeedbackCard로 포맷
     * who? 이미지-동사-명사 형태의 값을 가지고 있는 템플릿 사용 권장
     * */
    public List<FeedbackCardEntity> setFeedbackCardEntitiesFromWordCards(List<WordCardEntity> wordCardEntities) {
        List<FeedbackCardEntity> feedbackCardEntities = wordCardEntities.stream()
                .map(wordCard -> {
                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
                    feedbackCard.setImage(wordCard.getImage());
                    feedbackCard.setMeaning(wordCard.getMeaning());
                    feedbackCard.setDescription(wordCard.getDescription());
                    return feedbackCard;
                })
                .collect(Collectors.toList());
        return feedbackCardEntities;
    }

    public boolean isValidate(Long templateId, TemplateType templateType) {
        switch (templateType) {
            case TEMPLATE1:
                return template1Repository.existsById(templateId);
            case TEMPLATE2:
                return template2Repository.existsById(templateId);
            case TEMPLATE3:
                return template3Repository.existsById(templateId);
            case TEMPLATE4:
                return template4Repository.existsById(templateId);
            case TEMPLATE5:
                return template5Repository.existsById(templateId);
            default:
                return false;
        }
    }

}
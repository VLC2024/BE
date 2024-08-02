//package com.vlc.maeummal.domain.feedback.service;
//
//import com.vlc.maeummal.domain.feedback.dto.FeedbackRequestDTO;
//import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
//import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
//import com.vlc.maeummal.domain.feedback.repository.FeedbackRepository;
//import com.vlc.maeummal.domain.member.repository.MemberRepository;
//import com.vlc.maeummal.domain.template.common.Template;
//import com.vlc.maeummal.domain.template.common.TemplateRepository;
//import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
//import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
//import com.vlc.maeummal.global.common.BaseEntity;
//import com.vlc.maeummal.global.common.TemplateType;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class FeedbackService extends BaseEntity {
//
//    final TemplateRepository templateRepository;
//    final MemberRepository memberRepository;
//    final FeedbackRepository feedbackRepository;
//
//    /**
//     * 1. 데이터 가져오기
//     * - 템플릿 이미지,정답 set
//     * - 학생이 작성한 정답 set
//     * <p>
//     * 2. feedbackcard 에 각각 저장하기
//     * <p>
//     * 3. feedback 에 두개 리스트로 나눠서 넣기
//     * <p>
//     * TOdo. ai 피드백 생성해서 저장하기
//     */
//
//    public void setFeedbackFromAnswer(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
//        Long templateId = studentAnswerDTO.getTemplateId();
//        List<String> answerList = studentAnswerDTO.getAnswerList();
//        Optional<Template> template = templateRepository.findById(templateId);
//        log.info(" in setFeedbackFromAnswer: ");
//        if(getTemplateType(template).equals(TemplateType.TEMPLATE3)){
//            processTemplate3ToFeedback(template.get(), answerList, studentAnswerDTO);
//
//        }
//
//    }
//
//    public void processTemplate3ToFeedback(Template template, List<String> list, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
//        Template3Entity template3 = (Template3Entity) template;
//        List<String> answerList = list;
//        List<ImageCardEntity> imageCardEntities = template3.getImageCardEntityList();
//        log.info(" in processTemplate3ToFeedback: 1 ");
//
//        FeedbackEntity feedbackEntity = setFeedbackEntity(template3, studentAnswerDTO, imageCardEntities); // Todo 반환값 설정
//        log.info(" in processTemplate3ToFeedback: 2 ");
//        feedbackRepository.save(feedbackEntity);
//
//
//    }
//    public List<FeedbackCardEntity> setFeedbackCardEntity(List<ImageCardEntity> imageCardEntities) {
//
//        // 1. 이미지 카드 엔티티를 피드백 카드 엔티티로 변환
//        List<FeedbackCardEntity> feedbackCardEntities = imageCardEntities.stream()
//                .map(imageCard -> {
//                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
//                    feedbackCard.setImage(imageCard.getImage()); // image 필드 복사
//                    feedbackCard.setAdjective(imageCard.getAdjective()); // adjective 필드 복사
//                    feedbackCard.setNoun(imageCard.getNoun()); // noun 필드 복사
//
//                    return feedbackCard;
//                })
//                .collect(Collectors.toList());
//       return feedbackCardEntities;
//
//    }
//    public List<FeedbackCardEntity> setStudentFeedbackCardEntity(List<ImageCardEntity> imageCardEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
//        // 예외 처리: 리스트가 null인지 확인
//        if (imageCardEntities == null || studentAnswerDTO == null) {
//            throw new IllegalArgumentException("Image card entities and student answer DTO cannot be null");
//        }
//
//        // 예외 처리: 학생의 답변 리스트가 비어있는 경우
//        List<String> answerList = studentAnswerDTO.getAnswerList();
//        if (answerList == null) {
//            throw new IllegalArgumentException("Student answer list cannot be null");
//        }
//        if (answerList.size() != imageCardEntities.size()) {
//            throw new IllegalArgumentException("Answer list size does not match the image card entities size");
//        }
//
//        // 2. 학생이 작성한 엔터티를 피드백 카드 엔티티로 변환
//        List<FeedbackCardEntity> studentFeedbackCardEntities = IntStream.range(0, imageCardEntities.size())
//                .mapToObj(index -> {
//                    ImageCardEntity imageCard = imageCardEntities.get(index);
//                    String studentAdjective = studentAnswerDTO.getAnswerList().size() > index
//                            ? studentAnswerDTO.getAnswerList().get(index)
//                            : null;
//
//                    FeedbackCardEntity feedbackCard = new FeedbackCardEntity();
//                    feedbackCard.setImage(imageCard.getImage()); // image 필드 복사
//                    feedbackCard.setAdjective(studentAdjective); // adjective 필드 복사
//                    feedbackCard.setNoun(imageCard.getNoun()); // noun 필드 복사
//
//                    return feedbackCard;
//                })
//                .collect(Collectors.toList());
//        return studentFeedbackCardEntities;
//    }
//    public FeedbackEntity setFeedbackEntity(Template3Entity template3, FeedbackRequestDTO.GetAnswer studentAnswerDTO,
//                                            List<ImageCardEntity> imageCardEntities ) {
//        /**
//         *  @Id
//         *     @GeneratedValue(strategy = GenerationType.IDENTITY)
//         *     @Column(name = "feedback_id")
//         *     private Long id;
//         *
//         *     private Long templateId;
//         *
//         *     private String aiFeedback;
//         *
//         *     @ManyToOne(fetch = FetchType.LAZY)
//         *     @JoinColumn(name = "student_id")
//         *     private MemberEntity student;
//         *
//         *     @ManyToOne(fetch = FetchType.LAZY)
//         *     @JoinColumn(name = "teacher_id")
//         *     private MemberEntity teacher;
//         *
//         *     @Enumerated(EnumType.STRING)
//         *     private TemplateType templateType;
//         *
//         *     @Column(nullable = true)
//         *     private Integer imageNum;
//         *
//         *     @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
//         *     private List<FeedbackCardEntity> correctFeedbackCards = new ArrayList<>();
//         *
//         *     @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
//         *     private List<FeedbackCardEntity> studentFeedbackCards = new ArrayList<>();
//         * */
//        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
//                .templateId(template3.getId())
//                .aiFeedback("template ai feedback....")
//                .templateType(template3.getType()) // Enum 변환
//                .imageNum(template3.getImageNum())
//                .student(memberRepository.findById(studentAnswerDTO.getStudentEmail()).orElse(null)) // MemberEntity 조회
//                .teacher(memberRepository.findById(studentAnswerDTO.getStudentEmail()).orElse(null)) // MemberEntity 조회 Todo 교사로 수정해줘야함
//                .correctFeedbackCards(setFeedbackCardEntity(imageCardEntities))
//                .studentFeedbackCards(setStudentFeedbackCardEntity(imageCardEntities, studentAnswerDTO))
//                .build();
//
//        return feedbackEntity;
//    }
//
//    public TemplateType getTemplateType(Optional<Template> optionalTemplate) {
//        if (optionalTemplate.isPresent()) {
//            Template template = optionalTemplate.get();
//
//            if (template instanceof Template3Entity) {
//                return TemplateType.TEMPLATE3;
//            } else {
//                return TemplateType.UNKNOWN;
//            }
//        }
//        else {
//            return TemplateType.UNKNOWN;
//        }
//    }
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
package com.vlc.maeummal.domain.feedback.service;

import com.vlc.maeummal.domain.feedback.dto.FeedbackRequestDTO;
import com.vlc.maeummal.domain.feedback.dto.FeedbackResponseDTO;
import com.vlc.maeummal.domain.feedback.entity.FeedbackCardEntity;
import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.feedback.repository.FeedbackRepository;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import com.vlc.maeummal.domain.template.template3.entity.Template3Entity;
import com.vlc.maeummal.domain.template.template3.repository.Template3Repository;
import com.vlc.maeummal.global.common.BaseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    final MemberReposirotyUsingId memberRepository;
    final FeedbackRepository feedbackRepository;

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

    public List<FeedbackResponseDTO.GetFeedbackDTO> getAllFeedback() {
        List<FeedbackEntity> feedbackEntities = feedbackRepository.findAll();
        return feedbackEntities.stream()
                .map(this::mapToGetFeedbackDTO)
                .collect(Collectors.toList());
    }

    private FeedbackResponseDTO.GetFeedbackDTO mapToGetFeedbackDTO(FeedbackEntity feedbackEntity) {
        // 제목을 templateId로 조회
        String title = template3Repository.findById(feedbackEntity.getTemplateId())
                .map(Template3Entity::getDescription)// TOdo 제목으로 수정해야함(lesson생성 후)
                .orElse("Unknown");

        // DTO로 변환
        return FeedbackResponseDTO.GetFeedbackDTO.getFeedback(feedbackEntity, title);
    }

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

    public void setFeedbackFromAnswer(FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        Long templateId = studentAnswerDTO.getTemplateId();
        List<String> answerList = studentAnswerDTO.getAnswerList();
        Optional<Template3Entity> template3 = template3Repository.findById(templateId);
        log.info("in setFeedbackFromAnswer: ");

        if (template3.isPresent()) {
            processTemplate3ToFeedback(template3.get(), answerList, studentAnswerDTO);
        }

    }

    public void processTemplate3ToFeedback(Template3Entity template3, List<String> answerList, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
        List<ImageCardEntity> imageCardEntities = template3.getImageCardEntityList();
        log.info("in processTemplate3ToFeedback: 1");

        FeedbackEntity feedbackEntity = setFeedbackEntity(template3, studentAnswerDTO, imageCardEntities);
        log.info("in processTemplate3ToFeedback: 2");
        feedbackRepository.save(feedbackEntity);

    }

    public List<FeedbackCardEntity> setFeedbackCardEntity(List<ImageCardEntity> imageCardEntities) {
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

    public List<FeedbackCardEntity> setStudentFeedbackCardEntity(List<ImageCardEntity> imageCardEntities, FeedbackRequestDTO.GetAnswer studentAnswerDTO) {
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

    public FeedbackEntity setFeedbackEntity(Template3Entity template3, FeedbackRequestDTO.GetAnswer studentAnswerDTO,
                                            List<ImageCardEntity> imageCardEntities) {
        FeedbackEntity feedbackEntity = FeedbackEntity.builder()
                .templateId(template3.getId())
                .aiFeedback("template ai feedback....")
                .templateType(template3.getType())
                .imageNum(template3.getImageNum())
                .student(memberRepository.findById(studentAnswerDTO.getStudentId())
                        .orElse(null))
                .teacher(memberRepository.findById(studentAnswerDTO.getStudentId())
                        .orElse(null)) // Todo: 교사로 수정해야 함
                .build();

        feedbackEntity.setCorrectFeedbackCards(setFeedbackCardEntity(imageCardEntities));
        feedbackEntity.setStudentFeedbackCards(setStudentFeedbackCardEntity(imageCardEntities, studentAnswerDTO));

        return feedbackEntity;
    }
}

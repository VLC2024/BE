//package com.vlc.maeummal.domain.myPage.service;
//
//import com.vlc.maeummal.domain.myPage.entity.MatchingEntity;
//import com.vlc.maeummal.domain.myPage.repository.MatchingListRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import java.util.List;
//@Service@RequiredArgsConstructor@Transactional
//public class StudentServiceImpl implements StudentService {
//    private final MatchingListRepository matchingRepository;
////    private final MemberRepository memberRepository;
//
//
//    @Override
//    public List<MatchingEntity> getStudentList(Long userId) {
//        return null;
//    }
//
//    @Override
//    public List<MatchingEntity> getFollowerList(Long userId) {
//        return null;
//    }
//
//    @Override
//    public void addNewFriend(MatchingEntity matching) {
//        // 매칭 생성
//
//
//        // 매칭 추가
//        MatchingEntity newMatchingList = MatchingEntity.builder()
//                .teacher(matching.getTeacher())
//                .student(matching.getStudent())
//                .build();
//
//        matchingRepository.save(newMatchingList);
//
//    }
//
//    @Override
//    public void deleteFriend(Long userId, Long memberId) {
//
//    }
//}

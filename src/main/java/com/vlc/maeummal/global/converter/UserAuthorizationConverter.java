////package com.vlc.maeummal.global.converter;
////
////import com.vlc.maeummal.domain.member.entity.MemberEntity;
////import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
////import lombok.RequiredArgsConstructor;
////import org.springframework.security.core.Authentication;
////import org.springframework.security.core.context.SecurityContextHolder;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
////import org.springframework.stereotype.Component;
////@RequiredArgsConstructor
////@Component
////public class UserAuthorizationConverter {
////    private final MemberReposirotyUsingId memberRepository;
////
////    public Long getCurrentUserId() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////
////        System.out.println("authentication: "+authentication);
////        System.out.println(authentication.getPrincipal());
////        if ((authentication != null) && (authentication.getPrincipal()!=null)) {
////            Object principal = authentication.getPrincipal();
////            String userId = (String) principal;
////
////
////            MemberEntity member = memberRepository.findById(Long.parseLong(userId)).get();
////            if (member == null) {
////                throw new UsernameNotFoundException("User not found with id: " + member);
////            }
////            return member.getMemberId();
////        }
////        return null; // 인증된 사용자가 없을 경우 null 반환
////    }
////}
//package com.vlc.maeummal.global.converter;
//
//import com.vlc.maeummal.domain.member.entity.MemberEntity;
//import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
//import com.vlc.maeummal.domain.member.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.Optional;
//
//@RequiredArgsConstructor
//@Component
//public class UserAuthorizationConverter {
//    private final MemberRepository memberRepository;
//    private final MemberReposirotyUsingId memberReposirotyUsingId;
//
//    public Long getCurrentUserId() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication != null && authentication.getPrincipal() != null) {
//            Object principal = authentication.getPrincipal();
//            Long userId;
//
//            if (principal instanceof String) {
//                // principal이 String 타입인 경우 (사용자 ID로 가정)
//                try {
//                    userId = Long.parseLong((String) principal);
//                } catch (NumberFormatException e) {
//                    throw new UsernameNotFoundException("Invalid user ID format: " + principal);
//                }
//            } else if (principal instanceof UserDetails) {
//                // principal이 UserDetails 타입인 경우
//                String username = ((UserDetails) principal).getUsername();
//                Optional<MemberEntity> memberOptional = Optional.ofNullable(memberRepository.findByEmail(username));
//                if (memberOptional.isEmpty()) {
//                    throw new UsernameNotFoundException("User not found with email: " + username);
//                }
//                userId = memberOptional.get().getMemberId();
//            } else {
//                throw new UsernameNotFoundException("User not found with principal: " + principal);
//            }
//
//            // ID로 MemberEntity를 찾습니다.
//            Optional<MemberEntity> memberOptional = memberReposirotyUsingId.findById(userId);
//            if (memberOptional.isEmpty()) {
//                throw new UsernameNotFoundException("User not found with ID: " + userId);
//            }
//
//            return memberOptional.get().getMemberId();
//        }
//        return null; // 인증된 사용자가 없을 경우 null 반환
//    }
//}

package com.vlc.maeummal.global.converter;


import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.member.repository.MemberReposirotyUsingId;
import com.vlc.maeummal.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthorizationConverter {
    private final MemberReposirotyUsingId memberReposirotyUsingId;
    private final MemberRepository memberRepository;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("authentication: "+authentication);
        System.out.println(authentication.getPrincipal());
        if ((authentication != null) && (authentication.getPrincipal()!=null)) {
            String memberEmail = (String) authentication.getPrincipal();
            MemberEntity member = memberRepository.findByEmail(memberEmail);
            if (member == null) {
                throw new UsernameNotFoundException("member not found with username: " + memberEmail);
            }
            return member.getMemberId();
        }
        return null; // 인증된 사용자가 없을 경우 null 반환
    }
}


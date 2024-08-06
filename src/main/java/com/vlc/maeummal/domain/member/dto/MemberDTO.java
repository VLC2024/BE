package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.feedback.entity.FeedbackEntity;
import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.global.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class MemberDTO {
    private Long id;
    private String email;
    private String password;
    private String token;
}

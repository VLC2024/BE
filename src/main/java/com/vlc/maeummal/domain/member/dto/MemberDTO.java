package com.vlc.maeummal.domain.member.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
@Data
@Builder
public class MemberDTO {
    private Long id;
    private String email;
    private String password;
    private String token;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTokenDTO{

        private String token;

        public static MemberDTO.GetTokenDTO getTokenDTO (String token) {
            return MemberDTO.GetTokenDTO.builder()
                    .token(token)
                    .build();
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetProfileImageDTO{

        private String profileImage;

        public static MemberDTO.GetProfileImageDTO getProfileImageDTO (String profileImage) {
            return MemberDTO.GetProfileImageDTO.builder()
                    .profileImage(profileImage)
                    .build();
        }
    }
}

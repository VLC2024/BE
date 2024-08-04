package com.vlc.maeummal.domain.member.dto;

import com.vlc.maeummal.domain.member.entity.MemberEntity;
import com.vlc.maeummal.domain.template.template3.dto.Template3ResponseDTO;
import com.vlc.maeummal.domain.template.template3.entity.ImageCardEntity;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDTO {

    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String station;
    private String nickname;
    private String age;
    private String gender;
    private String image;
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
}

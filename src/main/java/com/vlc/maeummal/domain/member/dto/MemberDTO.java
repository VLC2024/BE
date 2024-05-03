package com.vlc.maeummal.domain.member.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberDTO {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String station;
    private String nickname;
    private String age;
    private String gender;
    private String image;
}

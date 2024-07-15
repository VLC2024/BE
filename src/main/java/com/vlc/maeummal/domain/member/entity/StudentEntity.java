package com.vlc.maeummal.domain.member.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

public class StudentEntity extends MemberEntity{

    @Id
    @GeneratedValue
    private Long id;
    private Integer iq;
}

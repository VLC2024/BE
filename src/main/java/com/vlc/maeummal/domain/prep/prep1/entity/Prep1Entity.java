package com.vlc.maeummal.domain.prep.prep1.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prep1")
public class Prep1Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "situation", length = 20)
    private String situation;

    @Column(name = "detailed_situation", length = 50)
    private String detailedSituation;

    @Column(name = "answer", length = 20)
    private String answer;

    @Column(name = "explanation", length = 50)
    private String explanation;

    @Column(name = "uid")
    private Long uid;

    @Column(name = "option1", length = 30)
    private String option1;

    @Column(name = "option2", length = 30)
    private String option2;

    @Column(name = "option3", length = 30)
    private String option3;

    @Column(name = "option4", length = 30)
    private String option4;

    private String imageUrl;

}

package com.vlc.maeummal.domain.prep.prep2.entity;

import com.vlc.maeummal.global.enums.Category;
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
@Table(name = "prep2")
public class Prep2Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prep2_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable=true)
    private String prompt;

    @Column(nullable=true)
    private String noun1;

    @Column(nullable=true)
    private String noun2;

    @Column(nullable=true)
    private String noun3;

    @Column(nullable=true)
    private String adv1;

    @Column(nullable=true)
    private String adv2;

    @Column(nullable=true)
    private String adv3;

    @Column(nullable=true)
    private String verb1;

    @Column(nullable=true)
    private String verb2;

    @Column(nullable=true)
    private String verb3;

    @Column(nullable=true)
    private String sentence;

    @Column(nullable=true)
    private String img;
}

package com.vlc.maeummal.domain.template.template3.entity;

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
@Table(name = "ImageCard")
public class ImageCardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageCard_id")
    private Long id;

    @Column(nullable = true)
    private String image;
    @Column(nullable=true)
    private String adjective;
    @Column(nullable=true)
    private String noun;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template3Id")
    private Template3Entity template3;

    @Column(nullable=true)
    private String hint;

}

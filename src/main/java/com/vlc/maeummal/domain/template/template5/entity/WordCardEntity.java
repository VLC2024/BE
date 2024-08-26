package com.vlc.maeummal.domain.template.template5.entity;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.global.common.BaseEntity;
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
@Table(name = "wordCard")
public class WordCardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wordCard_id")
    private Long id;

    @Column
    private Long wordId;
    @Column
    private String image;
    @Column
    private String meaning;
    @Column
    private String description;
    @Column
    private Long wordsetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "temp5_id")
    private Template5Entity temp5;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private WordEntity word;
*/

}

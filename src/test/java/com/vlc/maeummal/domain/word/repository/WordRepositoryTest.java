package com.vlc.maeummal.domain.word.repository;

import com.vlc.maeummal.domain.word.entity.WordEntity;
import com.vlc.maeummal.domain.word.entity.WordSetEntity;
import com.vlc.maeummal.global.enums.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(true)
class WordRepositoryTest {
    @Autowired WordRepository wordRepository;
    @Autowired
    WordSetRepository wordSetRepository;
    /*
    * @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column(nullable=true)
    private String meaning;
    @Column(nullable=true)
    private String image;
    @Column(nullable=true)
    private String prompt;
    @Column(nullable=true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wordSetId")
    private WordSetEntity wordSetId;
    *
    *
    * */

    @Test
    void testCreateAndRetrieveWordEntity() {

        // given
        // WordSetEntity 생성
        WordSetEntity wordSet = WordSetEntity.builder()
                .title("Sample Word Set")
                .description("A sample word set description")
                .category(Category.FOOD)
                .build();
        // WordSetEntity 저장
        wordSet = wordSetRepository.save(wordSet);


        WordEntity wordEntity = WordEntity.builder()
                .meaning("apple")
                .image("sample_image.png")
                .prompt("Sample Prompt")
                .description("appleapple")
                .wordSet(wordSet)
                .build();
        // then

            // wordEntity 저장
        wordEntity =  wordRepository.save(wordEntity);


        // when
        // 저장된 WordEntity ID로 조회
        Optional<WordEntity> foundWordEntity = wordRepository.findById(wordEntity.getId());

        assertThat(foundWordEntity).isPresent();
        assertThat(foundWordEntity.get().getMeaning()).isEqualTo("apple");
        assertThat(foundWordEntity.get().getImage()).isEqualTo("sample_image.png");
        assertThat(foundWordEntity.get().getPrompt()).isEqualTo("Sample Prompt");
        assertThat(foundWordEntity.get().getDescription()).isEqualTo("appleapple");
        assertThat(foundWordEntity.get().getWordSet()).isEqualTo(wordSet);

        assertThat(foundWordEntity.get().getWordSet().getId()).isEqualTo(wordSet.getId());

    }

    @Test
    void findById() {
        // given
        // then
        // when
    }

    @Test
    void findByWordSetId() {
        // given
        // then
        // when
    }
}
<<<<<<< HEAD
package com.vlc.maeummal.domain.word.dto;

import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.domain.word.repository.WordSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(true)
class WordSetResponseDTOTest {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    WordSetRepository wordSetRepository;

    /**
     * word set
     *
     *
     * */

=======
package com.vlc.maeummal.domain.word.dto;

import com.vlc.maeummal.domain.word.repository.WordRepository;
import com.vlc.maeummal.domain.word.repository.WordSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@Rollback(true)
class WordSetResponseDTOTest {
    @Autowired
    WordRepository wordRepository;
    @Autowired
    WordSetRepository wordSetRepository;

    /**
     * word set
     *
     *
     * */

>>>>>>> e9bb128834b7ea796bc561de7d7ec6bab4b723e5
}
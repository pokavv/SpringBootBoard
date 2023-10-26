package study.board.service;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Board;
import study.board.domain.User;
import study.board.repository.BoardRepository;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired BoardService boardService;
    @Autowired BoardRepository boardRepository;
    @Autowired EntityManager em;

    @Test
    public void createBoard() {
        // given
        User user = User.builder().loginId("userA").build();
        em.persist(user);

        // when
        Long postId = boardService.create("제목", "내용", user.getId());
        Board posting = boardRepository.findById(postId).orElseThrow();
        log.info("게시글 정보 - 제목={}, 내용={}, 작성자={}",
                posting.getTitle(), posting.getContent(), posting.getUser().getLoginId());

        // then
        assertThat(posting.getTitle()).isEqualTo("제목");
        assertThat(posting.getContent()).isEqualTo("내용");
        assertThat(posting.getUser()).isEqualTo(user);
    }

    @Test
    public void updateBoard() {
        // given
        User user = User.builder().loginId("userA").build();
        em.persist(user);
        Long postId = boardService.create("제목", "내용", user.getId());

        // when
        Board posting = boardRepository.findById(postId).orElseThrow();
        log.info("원본 게시글 정보 - 제목={}, 내용={}, 작성자={}",
                posting.getTitle(), posting.getContent(), posting.getUser().getLoginId());

        boardService.updateBoard(postId, "수정한 제목", "수정한 내용");
        log.info("수정 게시글 정보 - 제목={}, 내용={}, 작성자={}",
                posting.getTitle(), posting.getContent(), posting.getUser().getLoginId());

        // then
        assertThat(posting.getTitle()).isEqualTo("수정한 제목");
        assertThat(posting.getContent()).isEqualTo("수정한 내용");
        assertThat(posting.getUser()).isEqualTo(user);

    }
}

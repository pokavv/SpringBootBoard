package study.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Board;
import study.board.domain.User;
import study.board.repository.BoardRepository;
import study.board.repository.UserRepository;

import java.util.HashMap;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    /**
     * 게시글 작성
     */
    @Transactional
    public Long create(String title, String content, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Board board = Board.createBoard(title, content, user);
        boardRepository.save(board);
        return board.getId();
    }

    /**
     * 게시글 조회
     * findOnePost : 게시글 1개 조회
     * findAllPost : 게시글 전체 조회
     */

    public Optional<Board> findOnePost(Long id) {
        return boardRepository.findById(id);
    }

    /**
     * 게시글 전체 조회
     */
    @Query(
            value = "SELECT * FROM board ORDER BY id",
            countQuery = "SELECT count(*) FROM board",
            nativeQuery = true
    )
    public HashMap<String, Object> findAllPost(Pageable page) {
        HashMap<String, Object> listMap = new HashMap<>();
        Page<Board> list = boardRepository.findAll(page);

        listMap.put("list", list);
        listMap.put("paging", list.getPageable());
        listMap.put("totalCnt", list.getTotalElements());
        listMap.put("totalPage", list.getTotalPages());
        return listMap;
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public void updateBoard(Long id, String title, String content) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.update(title, content);
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}

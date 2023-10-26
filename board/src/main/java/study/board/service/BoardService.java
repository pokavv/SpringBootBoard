package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Board;
import study.board.domain.User;
import study.board.repository.BoardRepository;
import study.board.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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

    public List<Board> findAllPost() {
        return boardRepository.findAll();
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

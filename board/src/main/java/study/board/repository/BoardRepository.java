package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.board.domain.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
}

// 스프링 데이터 JPA 를 사용(상속)해서 간단하게 Repository 완성
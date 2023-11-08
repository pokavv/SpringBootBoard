package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.domain.file.FileRequest;

public interface FileRepository extends JpaRepository<FileRequest, Long> {
}

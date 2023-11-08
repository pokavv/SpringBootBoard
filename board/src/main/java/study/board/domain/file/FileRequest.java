package study.board.domain.file;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FileRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_Id")
    private Long id;

    private Long postId; // posting number (FK)

    private String originalName; // 원본 파일명

    private String saveName; // 저장 파일명

    private Long size;

    @Builder // 빌더 패턴 사용
    public FileRequest(Long id ,String originalName, String saveName, Long size) {
        this.id = id;
        this.originalName = originalName;
        this.saveName = saveName;
        this.size = size;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}

package study.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩 설정 (n+1 문제 방지)
    @JoinColumn(name = "user_id")
    private User user;

    private String createDate;

    @Builder
    public Board(String title, String content, User user, LocalDateTime createDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createDate);
    }

    /**
     * 생성 메서드
     */
    public static Board createBoard(String title, String content, User user) {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .createDate(LocalDateTime.now())
                .build();
    }

    /**
     * 수정 메서드
     */
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}

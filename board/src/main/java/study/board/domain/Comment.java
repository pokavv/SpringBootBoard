package study.board.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String createDate;

    @Builder
    public Comment(String comment, Board board, User user, LocalDateTime createDate) {
        this.comment = comment;
        this.board = board;
        this.user = user;
        this.createDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createDate);
    }

    /**
     * 댓글 생성
     */
    public static Comment addComment(String comment, Board board, User user) {
        return Comment.builder()
                .comment(comment)
                .board(board)
                .user(user)
                .createDate(LocalDateTime.now())
                .build();
    }
}


package study.board.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.User;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    @DisplayName("회원가입")
    void register() {
        // given
        User userA = User.builder().loginId("userA").build();

        // when
        Long registerId = userService.register(userA);

        // then
        Assertions.assertThat(userA).isEqualTo(userService.findById(registerId));
    }

    @Test
    @DisplayName("중복_회원예외")
    void registerDuplicate() {
        // given

        // when

        // then
    }
}

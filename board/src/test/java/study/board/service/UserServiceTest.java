package study.board.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
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
        assertThat(userA).isEqualTo(userService.findById(registerId).orElseThrow());
        log.info("welcome new user {}!", userA.getLoginId());
    }

    @Test
    @DisplayName("중복_회원예외")
    void registerDuplicate() {
        // given
        User userA = User.builder().loginId("user1").build();
        User userB = User.builder().loginId("user1").build();

        // when
        userService.register(userA);

        // then
        assertThatThrownBy(() -> userService.register(userB))
                .isInstanceOf(IllegalStateException.class);
    }
}

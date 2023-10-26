package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.User;
import study.board.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Long register(User user) {
        validationId(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validationId(User user) {
        List<User> findUsers = userRepository.findByLoginId(user.getLoginId());
        if (!findUsers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }
    }

    /**
     * 회원 조회
     */
    public User findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * 회원 전체 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }
}

package study.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.User;
import study.board.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;

    /**
     * 로그인
     */
    public User login(String loginId, String password) {
        Optional<User> findUser = userRepository.findByLoginId(loginId);
        User user = findUser.get();

        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }
}

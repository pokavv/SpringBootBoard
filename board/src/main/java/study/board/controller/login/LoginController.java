package study.board.controller.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.controller.SessionConst;
import study.board.domain.User;
import study.board.service.LoginService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm,
                        BindingResult bindingResult, HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "login/loginForm";
        }

        User loginUser = loginService.login(loginForm.getLoginId(), loginForm.getPassword());

        if (loginUser == null) {
            log.info("로그인 실패!");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        log.info("로그인 성공!");
        HttpSession session = request.getSession(); // 세션이 있으면 반환, 없으면 신규 세션 생성
        log.info("신규 세션 생성!");
        session.setAttribute(SessionConst.LOGIN_USER, loginUser); // 세션에 loginUser 정보 보관
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 제거
            log.info("로그아웃 완료");
        }
        return "redirect:/";
    }
}

/*
1. request.getSession(true) : default
 - 세션이 있으면 기존 세션을 반환
 - 세션이 없으면 새로운 세션을 생성해서 반환
2. request.getSession(false)
 - 세션이 있으면 기존 세션을 반환한다.
 - 세션이 없으면 새로운 세션을 생성x, null 반환
 */
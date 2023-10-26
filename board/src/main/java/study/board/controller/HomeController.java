package study.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import study.board.domain.User;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String loginHome(
            @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
            Model model) {
        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("loginUser", loginUser);
        return "loginHome";
    }
}

package study.board.controller.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.domain.User;
import study.board.service.UserService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/adduser")
    public String addUserForm(@ModelAttribute UserForm userForm) {
        log.info("adduserForm");
        return "users/adduserForm";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid @ModelAttribute UserForm userForm,
                         BindingResult bindingResult) {
        log.info("adduser");

        if (bindingResult.hasErrors()) {
            log.info("errors ={}", bindingResult);
            return "users/adduserForm";
        }

        User newUser = User.builder()
                .loginId(userForm.getLoginId())
                .password(userForm.getPassword())
                .name(userForm.getName())
                .age(userForm.getAge())
                .build();

        userService.register(newUser);

        log.info("환영합니다! 회원가입 성공");
        return "redirect:/";
    }
}

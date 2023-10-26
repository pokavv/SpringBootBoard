package study.board.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.board.controller.SessionConst;
import study.board.domain.Board;
import study.board.domain.User;
import study.board.service.BoardService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String postList(Model model) {
        model.addAttribute("list", boardService.findAllPost());
        return "board/postList";
    }

    @GetMapping("/{postId}")
    public String postView(@PathVariable Long postId, Model model) {
        log.info("postView");

        Board post = boardService.findOnePost(postId).orElseThrow();
        model.addAttribute("post", post);

        return "board/post";
    }

    @GetMapping("/add-post")
    public String addPostForm(@ModelAttribute PostForm postForm) {
        return "board/addPostForm";
    }

    @PostMapping("/add-post")
    public String addPost(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult,
                          @SessionAttribute(name = SessionConst.LOGIN_USER, required = false)
                          User loginUser) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/addPostForm";
        }

        boardService.create(postForm.getTitle(), postForm.getContent(), loginUser.getId());
        log.info("게시 완료");
        return "redirect:/board";
    }
}

package study.board.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String postList(Model model,
                           @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 5)
                           Pageable pageable) {
        log.info("postList");
        model.addAttribute("resultMap", boardService.findAllPost(pageable));
        return "board/postList";
    }

    @GetMapping("/{postId}")
    public String postView(@PathVariable Long postId, Model model) {
        Board post = boardService.findOnePost(postId).orElseThrow();
        model.addAttribute("post", post);
        log.info("postView");

        return "board/post";
    }

    @GetMapping("/add-post")
    public String addPostForm(@ModelAttribute PostForm postForm) {
        return "board/addPostForm";
    }

    @PostMapping("/add-post")
    public String addPost(@Valid @ModelAttribute PostForm postForm, BindingResult bindingResult,
                          @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/addPostForm";
        }

        Long postId = boardService.create(postForm.getTitle(), postForm.getContent(), loginUser.getId());
        log.info("게시 완료");
        redirectAttributes.addAttribute("postId", postId);
        return "redirect:/board/{postId}";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {
        Board post = boardService.findOnePost(postId).orElseThrow();

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", postId);

        return "board/editForm";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId,
                       @Valid @ModelAttribute PostForm postForm,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return "board/editForm";
        }

        boardService.updateBoard(postId, postForm.getTitle(), postForm.getContent());
        return "redirect:/board/{postId}";
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        boardService.deleteById(postId);
        return "redirect:/board";
    }
}

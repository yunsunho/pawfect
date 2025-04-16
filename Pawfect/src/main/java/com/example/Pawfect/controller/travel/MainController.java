package com.example.Pawfect.controller.travel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Pawfect.service.BoardService;

@Controller
public class MainController {
	@Autowired
	private BoardService boardService;

    @GetMapping("/main")
    public String mainPage(Model model) {
        model.addAttribute("topViewedPosts", boardService.getTopPostsByViews());
        model.addAttribute("topLikedPosts", boardService.getTopPostsByLikes());
        model.addAttribute("topCommentedPosts", boardService.getTopPostsByComments());
        return "travel/main";
    }
}

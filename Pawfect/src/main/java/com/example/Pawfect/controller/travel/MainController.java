package com.example.Pawfect.controller.travel;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/main")
    public String mainPage() {
        return "travel/main"; // main.jsp 또는 main.html이 렌더링됨
    }
}
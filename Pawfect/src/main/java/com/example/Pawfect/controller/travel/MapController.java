package com.example.Pawfect.controller.travel;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

	@GetMapping("/map")
	public String mapPage(Model model) {
	    model.addAttribute("currentPage", "map");
	    return "travel/map"; // travel 폴더 아래 map.jsp
	}

}
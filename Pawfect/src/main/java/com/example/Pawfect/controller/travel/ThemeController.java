package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.example.Pawfect.service.BookmarkService;

@Controller
@RequiredArgsConstructor
public class ThemeController {

    @Value("${api.service-key}")
    private String serviceKey;
    private final BookmarkService bookmarkService;

    // 👉 테마 리스트 뷰 이동
    @GetMapping("/themeList")
    public String themeListPage(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (userId != null) {
            List<Integer> myBookmarks = bookmarkService.findContentIdsByUserId(userId);
            model.addAttribute("myBookmarks", myBookmarks);
        }

        //model.addAttribute("currentPage", "theme"); // 기존에 있던 속성 유지
        return "travel/themeList"; // 너의 JSP 이름에 맞게
    }



    // 👉 AJAX 테마 목록 데이터
    @GetMapping("/api/themeData")
    @ResponseBody
    public Map<String, Object> getThemeData(
            @RequestParam int contentTypeId,
            @RequestParam(defaultValue = "O") String arrange,
            @RequestParam(defaultValue = "1") int pageNo) throws Exception {

        String encoded = URLEncoder.encode(serviceKey, "UTF-8");
        List<Map<String, String>> themeList = new ArrayList<>();

        String url = "https://apis.data.go.kr/B551011/KorPetTourService/petTourSyncList?"
                + "serviceKey=" + encoded
                + "&numOfRows=20"
                + "&pageNo=" + pageNo
                + "&MobileOS=ETC"
                + "&MobileApp=PawfectTour"
                + "&arrange=" + arrange
                + "&contentTypeId=" + contentTypeId
                + "&_type=json";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.exchange(
                new URI(url),
                HttpMethod.GET,
                new HttpEntity<>(new HttpHeaders()),
                String.class
        );

        JsonNode body = mapper.readTree(response.getBody()).path("response").path("body");
        JsonNode items = body.path("items").path("item");
        int totalCount = body.path("totalCount").asInt();

        for (JsonNode item : items) {
            themeList.add(Map.of(
                    "contentid", item.path("contentid").asText(),
                    "contenttypeid", item.path("contenttypeid").asText(), 
                    "title", item.path("title").asText(),
                    "addr1", item.path("addr1").asText(),
                    "firstimage", item.path("firstimage").asText(),
                    "mapx", item.path("mapx").asText(),
                    "mapy", item.path("mapy").asText()
            ));
        }

        int totalPages = (int) Math.ceil((double) totalCount / 20);

        return Map.of(
                "list", themeList,
                "totalPages", totalPages
        );
    }
}

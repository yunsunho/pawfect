package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.service.BookmarkService;
import com.example.Pawfect.service.ReviewService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequiredArgsConstructor
public class ThemeController {

    @Value("${api.service-key}")
    private String serviceKey;

    private final BookmarkService bookmarkService;
    private final ReviewService reviewService;

    // 👉 테마 리스트 뷰 이동
    @GetMapping("/themeList")
    public String themeListPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String userId = userDetails.getUser().getUserId();

            List<Integer> myBookmarks = bookmarkService.findContentIdsByUserId(userId);
            model.addAttribute("myBookmarks", myBookmarks);
        }

        return "travel/themeList";
    }

    // 👉 AJAX 테마 목록 데이터
    @GetMapping("/api/themeData")
    @ResponseBody
    public Map<String, Object> getThemeData(
            @RequestParam int contentTypeId,
            @RequestParam(defaultValue = "O") String arrange,
            @RequestParam(defaultValue = "1") int pageNo) throws Exception {

        String encoded = URLEncoder.encode(serviceKey, "UTF-8");
        List<Map<String, Object>> themeList = new ArrayList<>();

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
            int contentId = item.path("contentid").asInt();
            double rating = reviewService.getAverageRating(contentId);
            int bookmarkCount = bookmarkService.countByContentId(contentId);

            Map<String, Object> place = new HashMap<>();
            place.put("contentid", String.valueOf(contentId));
            place.put("contenttypeid", item.path("contenttypeid").asText());
            place.put("title", item.path("title").asText());
            place.put("addr1", item.path("addr1").asText());
            place.put("firstimage", item.path("firstimage").asText());
            place.put("mapx", item.path("mapx").asText());
            place.put("mapy", item.path("mapy").asText());
            place.put("rating", String.format("%.1f", rating));
            place.put("bookmarkCount", bookmarkCount);
            themeList.add(place);
        }

        int totalPages = (int) Math.ceil((double) totalCount / 20);

        return Map.of(
                "list", themeList,
                "totalPages", totalPages
        );
    }
}

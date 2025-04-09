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

@Controller
public class ThemeController {

    @Value("${api.service-key}")
    private String serviceKey;

    // 👉 테마 리스트 뷰 이동
    @GetMapping("/themeList")
    public String themeListPage(@RequestParam(defaultValue = "12") int contentTypeId, Model model) {
        model.addAttribute("currentPage", "theme");
        model.addAttribute("showSubmenu", true);
        model.addAttribute("initialContentTypeId", contentTypeId); // JS에서 초기 설정에 사용
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

    // 👉 상세 페이지 요청 처리
//    @GetMapping("/detail/{contentId}")
//    public String getDetailInfo(
//            @PathVariable String contentId,
//            @RequestParam("contentTypeId") String contentTypeId,
//            Model model) throws Exception {
//
//        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");
//
//        // 🔹 이미지 API
//        String imageUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailImage?"
//                + "serviceKey=" + encodedKey
//                + "&MobileOS=ETC&MobileApp=PawfectTour"
//                + "&contentId=" + contentId
//                + "&imageYN=Y&_type=json";
//        
//        // System.out.println("🖼️ 호출되는 이미지 API URL: " + imageUrl);
//
//        RestTemplate restTemplate = new RestTemplate();
//        ObjectMapper mapper = new ObjectMapper();
//
//        ResponseEntity<String> imageResponse = restTemplate.exchange(
//                new URI(imageUrl),
//                HttpMethod.GET,
//                new HttpEntity<>(new HttpHeaders()),
//                String.class
//        );
//
//        JsonNode imageItems = mapper.readTree(imageResponse.getBody())
//                .path("response").path("body").path("items").path("item");
//
//        List<String> images = new ArrayList<>();
//        for (JsonNode item : imageItems) {
//            images.add(item.path("originimgurl").asText());
//        }
//
//        // 🔹 상세 정보 API - contentTypeId도 같이 포함
//        String detailUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailIntro?"
//                + "serviceKey=" + encodedKey
//                + "&MobileOS=ETC&MobileApp=PawfectTour"
//                + "&contentId=" + contentId
//                + "&contentTypeId=" + contentTypeId
//                + "&_type=json";
//        
//        ResponseEntity<String> detailResponse = restTemplate.exchange(
//                new URI(detailUrl),
//                HttpMethod.GET,
//                new HttpEntity<>(new HttpHeaders()),
//                String.class
//        );
//        
//        JsonNode detailItem = mapper.readTree(detailResponse.getBody())
//                .path("response").path("body").path("items").path("item").get(0);
//
//        Map<String, String> detail = Map.of(
//                "title", detailItem.path("title").asText(),
//                "addr1", detailItem.path("addr1").asText(),
//                "overview", detailItem.path("overview").asText(),
//                "mapx", detailItem.path("mapx").asText(),
//                "mapy", detailItem.path("mapy").asText()
//        );
//
//        model.addAttribute("detail", detail);
//        model.addAttribute("images", images);
//        // model.addAttribute("currentPage", "theme");  
//
//        return "travel/detail";
//    }

}

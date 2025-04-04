package com.example.Pawfect.controller;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TourController {
    @GetMapping("/tourlist")
    public String getAllTourList(Model model) throws Exception {
    	// 인코딩 안 된 원래 키 (공공데이터 포털에서 복사해온 원형)
    	String serviceKey = "/leCaqoLYYVmeyAYkuNsvs1fQEtCoHSfMZcTebr+oeVEfbrdqhUUTM4oEUKfwpX3r+hpC+XFc7hsktUcHW1OAg==";
    	String encoded = URLEncoder.encode(serviceKey, "UTF-8");
    	System.out.println("👉 인코딩 결과: " + encoded);
        int totalPages = 3;
        List<Map<String, String>> tourList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        for (int page = 1; page <= totalPages; page++) {
            String url = "https://apis.data.go.kr/B551011/KorPetTourService/petTourSyncList?"
                    + "serviceKey=" + encoded
                    + "&numOfRows=20"
                    + "&pageNo=" + page
                    + "&MobileOS=ETC"
                    + "&MobileApp=PawfectTour"
                    + "&arrange=O"
                    + "&_type=json";

            // 👉 여기부터 추가
            HttpHeaders headers = new HttpHeaders();
            headers.add("User-Agent", "Mozilla/5.0");
            
            HttpEntity<String> entity = new HttpEntity<>(headers);
            URI uri = new URI(url);
            ResponseEntity<String> response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                entity,
                String.class
            );

            String json = response.getBody(); // ✅ JSON 응답이 여기 들어옴
            System.out.println("📦 응답 확인:\n" + json); // 디버깅용

            JsonNode items = mapper.readTree(json)
                    .path("response").path("body").path("items").path("item");

            for (JsonNode item : items) {
                tourList.add(Map.of(
                        "contentid", item.path("contentid").asText(),
                        "title", item.path("title").asText(),
                        "addr1", item.path("addr1").asText(),
                        "firstimage", item.path("firstimage").asText(),
                        "mapx", item.path("mapx").asText(),
                        "mapy", item.path("mapy").asText()
                ));
            }
        }


        model.addAttribute("tourList", tourList);
        return "tourlist";
    }
}


package com.example.Pawfect.controller.travel;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RequestMapping("main")
@Controller
public class MainController {
    
    @Value("${api.service-key}")
    private String serviceKey;

    @GetMapping
    public String search(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") int pageNo,
            Model model) throws UnsupportedEncodingException, URISyntaxException {
    	
        List<Map<String, String>> areaList = new ArrayList<>();

        if (keyword == null || keyword.isBlank()) {
            model.addAttribute("areaList", areaList);
            model.addAttribute("totalCount", 0);
            model.addAttribute("pageNo", pageNo);
            return "travel/main";
        }

        try {
            String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");
            String encodedKeyword = URLEncoder.encode(keyword, "UTF-8");

            String url = "http://apis.data.go.kr/B551011/KorPetTourService/searchKeyword?"
                    + "serviceKey=" + encodedKey
                    + "&numOfRows=7"
                    + "&pageNo=" + pageNo
                    + "&MobileOS=ETC"
                    + "&MobileApp=PawfectTour"
                    + "&_type=json"
                    + "&keyword=" + encodedKeyword;

            System.out.println("Request URL: " + url); // Logging

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
                areaList.add(Map.of(
                        "contentid", item.path("contentid").asText(),
                        "contenttypeid", item.path("contenttypeid").asText(),
                        "title", item.path("title").asText(),
                        "addr1", item.path("addr1").asText(),
                        "firstimage", item.path("firstimage").asText(),
                        "mapx", item.path("mapx").asText(),
                        "mapy", item.path("mapy").asText()
                ));
            }

            model.addAttribute("areaList", areaList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("keyword", keyword);
            model.addAttribute("pageNo", pageNo);

        } catch (RestClientException | URISyntaxException | UnsupportedEncodingException e) {
            model.addAttribute("error", "API 호출 중 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            model.addAttribute("error", "알 수 없는 오류가 발생했습니다: " + e.getMessage());
            e.printStackTrace(); 
        }

        return "travel/search";
    }
}
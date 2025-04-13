package com.example.Pawfect.controller.travel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Value("${api.service-key}")
    private String serviceKey;

    // 👉 지도 페이지
    @GetMapping("/map")
    public String mapPage(Model model) {
        model.addAttribute("currentPage", "map");
        return "travel/map"; // travel 폴더 아래 map.jsp
    }

    // 👉 지도 데이터 API
    @GetMapping("/api/mapData")
    @ResponseBody
    public Map<String, Object> getMapData(
            @RequestParam double mapX,
            @RequestParam double mapY,
            @RequestParam(defaultValue = "8000") int radius,
            @RequestParam(defaultValue = "O") String arrange,
            @RequestParam(defaultValue = "") String contentTypeId) throws Exception {

        String encoded = URLEncoder.encode(serviceKey, "UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, String>> totalList = new ArrayList<>();
        int pageNo = 1;
        int totalCount;

        do {
            String url = "https://apis.data.go.kr/B551011/KorPetTourService/locationBasedList?" +
                    "serviceKey=" + encoded +
                    "&MobileOS=ETC&MobileApp=PawfectTour&_type=json" +
                    "&mapX=" + mapX +
                    "&mapY=" + mapY +
                    "&radius=" + radius +
                    "&arrange=" + arrange +
                    "&pageNo=" + pageNo +
                    "&numOfRows=100";

            if (!contentTypeId.isBlank()) {
                url += "&contentTypeId=" + contentTypeId;
            }

            ResponseEntity<String> response = restTemplate.exchange(
                    new URI(url),
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    String.class
            );

            JsonNode body = mapper.readTree(response.getBody()).path("response").path("body");
            JsonNode items = body.path("items").path("item");
            totalCount = body.path("totalCount").asInt();

            if (items.isArray() && items.size() > 0) {
                for (JsonNode item : items) {
                    totalList.add(Map.of(
                        "title", item.path("title").asText(),
                        "addr1", item.path("addr1").asText(),
                        "mapx", item.path("mapx").asText(),
                        "mapy", item.path("mapy").asText(),
                        "contentid", item.path("contentid").asText(),
                        "contenttypeid", item.path("contenttypeid").asText(),
                        "firstimage", item.path("firstimage").asText()
                    ));
                }
            } else {
                System.out.println("No items found");
                System.out.println(body.toString());
            }
            
            pageNo++;

        } while ((pageNo - 1) * 100 < totalCount);

        return Map.of("list", totalList);
    }
}

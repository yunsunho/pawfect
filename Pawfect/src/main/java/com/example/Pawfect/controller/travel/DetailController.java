package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DetailController {

    @Value("${api.service-key}")
    private String serviceKey;

    @GetMapping("/detail/{contentId}/{contentTypeId}")
    public String getDetailPage(
            @PathVariable String contentId,
            @PathVariable String contentTypeId,
            Model model) throws Exception {

        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // ✅ 공통 상세 정보 (detailCommon)
        String commonUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailCommon?"
                + "serviceKey=" + encodedKey
                + "&MobileOS=ETC&MobileApp=Pawfect"
                + "&contentId=" + contentId
                + "&defaultYN=Y&firstImageYN=N&areacodeYN=N&catcodeYN=N"
                + "&addrinfoYN=Y&mapinfoYN=Y&overviewYN=Y&_type=json";
        System.out.println("commonUrl" + commonUrl);
        ResponseEntity<String> commonResponse = restTemplate.exchange(
                new URI(commonUrl), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode common = mapper.readTree(commonResponse.getBody())
                .path("response").path("body").path("items").path("item").get(0);

        if (common == null || common.isMissingNode()) {
            model.addAttribute("detail", Map.of(
                "title", "데이터 없음",
                "addr1", "",
                "overview", "",
                "tel", "",
                "mapx", "0",
                "mapy", "0"
            ));
        } else {
        	Map<String, String> detailMap = new HashMap<>();
            detailMap.put("title", common.path("title").asText());
            detailMap.put("homepage", common.path("homepage").asText());
            detailMap.put("addr1", common.path("addr1").asText());
            detailMap.put("overview", common.path("overview").asText());
            detailMap.put("tel", common.path("tel").asText());
            detailMap.put("mapx", common.path("mapx").asText());
            detailMap.put("mapy", common.path("mapy").asText());
            model.addAttribute("detail", detailMap);
        }   

        // ✅ 이미지 API
        String imageUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailImage?"
                + "serviceKey=" + encodedKey
                + "&MobileOS=ETC&MobileApp=Pawfect"
                + "&contentId=" + contentId
                + "&imageYN=Y&_type=json";

        ResponseEntity<String> imageResponse = restTemplate.exchange(
                new URI(imageUrl), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode imageArray = mapper.readTree(imageResponse.getBody())
                .path("response").path("body").path("items").path("item");

        List<String> images = new ArrayList<>();
        if (imageArray.isArray()) {
            for (JsonNode img : imageArray) {
                images.add(img.path("originimgurl").asText());
            }
        }

        model.addAttribute("images", images);

        // ✅ 반려동물 정보 API
        String petUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailPetTour?"
                + "serviceKey=" + encodedKey
                + "&MobileOS=ETC&MobileApp=Pawfect"
                + "&contentId=" + contentId
                + "&_type=json";

        ResponseEntity<String> petResponse = restTemplate.exchange(
                new URI(petUrl), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode petNode = mapper.readTree(petResponse.getBody())
                .path("response").path("body").path("items").path("item").get(0);

        Map<String, String> petInfo = new HashMap<>();
        petInfo.put("chkpetfacility", petNode.path("chkpetfacility").asText());
        petInfo.put("chkpetroom", petNode.path("chkpetroom").asText());
        petInfo.put("chkpetrestaurant", petNode.path("chkpetrestaurant").asText());
        petInfo.put("petnotic", petNode.path("petnotic").asText());
        petInfo.put("petetc", petNode.path("petetc").asText());
        model.addAttribute("pet", petInfo);
        
        // 페이지 렌더링
        return "travel/detail";
    }
}

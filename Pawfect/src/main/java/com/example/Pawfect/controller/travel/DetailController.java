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
        	Map<String, String> detailCommon = new HashMap<>();
        	detailCommon.put("title", common.path("title").asText());
        	detailCommon.put("homepage", common.path("homepage").asText());
        	detailCommon.put("addr1", common.path("addr1").asText());
        	detailCommon.put("overview", common.path("overview").asText());
        	detailCommon.put("tel", common.path("tel").asText());
        	detailCommon.put("mapx", common.path("mapx").asText());
        	detailCommon.put("mapy", common.path("mapy").asText());
            model.addAttribute("common", detailCommon);
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

        Map<String, String> petTour = new HashMap<>();
        petTour.put("relaAcdntRiskMtr", petNode.path("relaAcdntRiskMtr").asText());
        petTour.put("acmpyTypeCd", petNode.path("acmpyTypeCd").asText());
        petTour.put("relaPosesFclty", petNode.path("relaPosesFclty").asText());
        petTour.put("relaFrnshPrdlst", petNode.path("relaFrnshPrdlst").asText());
        petTour.put("etcAcmpyInfo", petNode.path("etcAcmpyInfo").asText());
        petTour.put("relaPurcPrdlst", petNode.path("relaPurcPrdlst").asText());
        petTour.put("acmpyPsblCpam", petNode.path("acmpyPsblCpam").asText());
        petTour.put("relaRntlPrdlst", petNode.path("relaRntlPrdlst").asText());
        petTour.put("acmpyNeedMtr", petNode.path("acmpyNeedMtr").asText());
        model.addAttribute("pet", petTour);
        
        // ✅ 소개 정보 (detailIntro)
        String introUrl = "http://apis.data.go.kr/B551011/KorPetTourService/detailIntro?"
                + "serviceKey=" + encodedKey
                + "&contentId=" + contentId
                + "&contentTypeId=" + contentTypeId
                + "&MobileOS=ETC&MobileApp=Pawfect"                
                + "&_type=json";

        ResponseEntity<String> introResponse = restTemplate.exchange(
                new URI(introUrl), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode introNode = mapper.readTree(petResponse.getBody())
                .path("response").path("body").path("items").path("item").get(0);
        
        Map<String, String> detailIntro = new HashMap<>();

        switch (contentTypeId) {
            case "12": // 관광지
            	detailIntro.put("infocenter", introNode.path("infocenter").asText());
            	detailIntro.put("usetime", introNode.path("usetime").asText());
                break;
            case "32": // 숙박
            	detailIntro.put("roomcount", introNode.path("roomcount").asText());
            	detailIntro.put("checkintime", introNode.path("checkintime").asText());
            	detailIntro.put("checkouttime", introNode.path("checkouttime").asText());
                break;
            case "39": // 음식점
            	detailIntro.put("firstmenu", introNode.path("firstmenu").asText());
            	detailIntro.put("treatmenu", introNode.path("treatmenu").asText());
                break;
            // 그 외 타입도 필요한 만큼 추가
            default:
            	detailIntro.put("etc", "해당 타입의 상세정보 없음");
                break;
        }

        model.addAttribute("Intro", detailIntro);

        
        // 페이지 렌더링
        return "travel/detail";
    }
}

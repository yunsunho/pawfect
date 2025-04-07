package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
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

	    // ✅ 상세정보 API
	    String detailUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailIntro?"
	            + "serviceKey=" + encodedKey
	            + "&MobileOS=ETC&MobileApp=ETC"
	            + "&contentId=" + contentId
	            + "&contentTypeId=" + contentTypeId
	            + "&_type=json";

	    // ✅ 이미지 API
	    String imageUrl = "https://apis.data.go.kr/B551011/KorService1/detailImage?"
	            + "serviceKey=" + encodedKey
	            + "&MobileOS=ETC&MobileApp=PawfectTour"
	            + "&contentId=" + contentId
	            + "&imageYN=Y&_type=json";

	    // 공통 헤더
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("User-Agent", "Mozilla/5.0");
	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    // 요청
	    ResponseEntity<String> detailResponse = restTemplate.exchange(new URI(detailUrl), HttpMethod.GET, entity, String.class);
	    ResponseEntity<String> imageResponse = restTemplate.exchange(new URI(imageUrl), HttpMethod.GET, entity, String.class);

	    // 파싱
	    JsonNode detailNode = mapper.readTree(detailResponse.getBody())
	            .path("response").path("body").path("items").path("item").get(0);

	    JsonNode imageArray = mapper.readTree(imageResponse.getBody())
	            .path("response").path("body").path("items").path("item");

	    // Model에 담기
	    model.addAttribute("detail", detailNode);
	    model.addAttribute("images", imageArray);
	    model.addAttribute("contentId", contentId);

	    return "travel/detail";
	}
}

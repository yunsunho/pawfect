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
public class AreaController {

    @Value("${api.service-key}")
    private String serviceKey;

    private final BookmarkService bookmarkService;
    private final ReviewService reviewService;

    @GetMapping("/areaList")
    public String areaListPage(Model model) {
        model.addAttribute("currentPage", "area");
        model.addAttribute("showSubmenu", true);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String userId = userDetails.getUser().getUserId();

            List<Integer> bookmarks = bookmarkService.findContentIdsByUserId(userId);
            model.addAttribute("myBookmarks", bookmarks);
        }

        return "travel/areaList";
    }

    @GetMapping("/api/areaData")
    @ResponseBody
    public Map<String, Object> getAreaData(
            @RequestParam(required = false) String areaCode,
            @RequestParam(required = false) String sigunguCode,
            @RequestParam(defaultValue = "O") String arrange,
            @RequestParam(defaultValue = "1") int pageNo) throws Exception {

        int pageSize = 20;
        int offset = (pageNo - 1) * pageSize;

        List<Map<String, Object>> areaList = new ArrayList<>();

        // ⭐ 리뷰순 정렬일 경우 DB 사용
        if ("review".equals(arrange)) {
            List<Map<String, Object>> sorted = reviewService.getContentIdsSortedByReviewCountAndArea(areaCode, sigunguCode);
            int total = sorted.size();

            List<Map<String, Object>> paged = sorted.subList(Math.min(offset, total), Math.min(offset + pageSize, total));

            for (Map<String, Object> row : paged) {
                int contentId = (int) row.get("contentId");

                areaList.add(Map.of(
                    "contentid", String.valueOf(contentId),
                    "contenttypeid", String.valueOf(row.get("contentTypeId")),
                    "title", String.valueOf(row.get("title")),
                    "addr1", String.valueOf(row.get("addr1")),
                    "firstimage", String.valueOf(row.get("firstimage")),
                    "mapx", String.valueOf(row.get("mapX")),
                    "mapy", String.valueOf(row.get("mapY")),
                    "rating", String.format("%.1f", reviewService.getAverageRating(contentId)),
                    "bookmarkCount", String.valueOf(bookmarkService.countByContentId(contentId)),
                    "reviewCount", String.valueOf(reviewService.getTotalReviewCount(contentId))
                ));
            }

            int totalPages = (int) Math.ceil((double) total / pageSize);
            return Map.of("list", areaList, "totalPages", totalPages);
        } else if ("rating".equals(arrange)) {
            List<Map<String, Object>> sorted = reviewService.getContentIdsSortedByAvgRatingAndArea(areaCode, sigunguCode);
            int total = sorted.size();
            List<Map<String, Object>> paged = sorted.subList(Math.min(offset, total), Math.min(offset + pageSize, total));

            for (Map<String, Object> row : paged) {
                int contentId = (int) row.get("contentId");

                areaList.add(Map.of(
                    "contentid", String.valueOf(contentId),
                    "contenttypeid", String.valueOf(row.get("contentTypeId")),
                    "title", String.valueOf(row.get("title")),
                    "addr1", String.valueOf(row.get("addr1")),
                    "firstimage", String.valueOf(row.get("firstimage")),
                    "mapx", String.valueOf(row.get("mapX")),
                    "mapy", String.valueOf(row.get("mapY")),
                    "rating", String.format("%.1f", row.get("avgRating")),
                    "bookmarkCount", String.valueOf(bookmarkService.countByContentId(contentId)),
                    "reviewCount", String.valueOf(row.get("reviewCount"))
                ));
            }

            int totalPages = (int) Math.ceil((double) total / pageSize);
            return Map.of("list", areaList, "totalPages", totalPages);
        } else if ("bookmark".equals(arrange)) {
            List<Map<String, Object>> sorted = bookmarkService.getContentIdsSortedByBookmarkCountAndArea(areaCode, sigunguCode);
            int total = sorted.size();
            List<Map<String, Object>> paged = sorted.subList(Math.min(offset, total), Math.min(offset + pageSize, total));

            for (Map<String, Object> row : paged) {
                int contentId = (int) row.get("contentId");

                areaList.add(Map.of(
                    "contentid", String.valueOf(contentId),
                    "contenttypeid", String.valueOf(row.get("contentTypeId")),
                    "title", String.valueOf(row.get("title")),
                    "addr1", String.valueOf(row.get("addr1")),
                    "firstimage", String.valueOf(row.get("firstimage")),
                    "mapx", String.valueOf(row.get("mapX")),
                    "mapy", String.valueOf(row.get("mapY")),
                    "rating", String.format("%.1f", reviewService.getAverageRating(contentId)),
                    "bookmarkCount", String.valueOf(row.get("bookmarkCount")),
                    "reviewCount", String.valueOf(reviewService.getTotalReviewCount(contentId))
                ));
            }

            int totalPages = (int) Math.ceil((double) total / pageSize);
            return Map.of("list", areaList, "totalPages", totalPages);
        }

        // ⭐ 가나다/기타는 기존 API 방식 유지
        String encoded = URLEncoder.encode(serviceKey, "UTF-8");

        String url = "https://apis.data.go.kr/B551011/KorPetTourService/petTourSyncList?"
                + "serviceKey=" + encoded
                + "&numOfRows=" + pageSize
                + "&pageNo=" + pageNo
                + "&MobileOS=ETC"
                + "&MobileApp=PawfectTour"
                + "&arrange=" + arrange
                + (areaCode != null ? "&areaCode=" + areaCode : "")
                + (sigunguCode != null && !sigunguCode.isEmpty() ? "&sigunguCode=" + sigunguCode : "")
                + "&_type=json";

        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        ResponseEntity<String> response = restTemplate.exchange(
                new URI(url), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode body = mapper.readTree(response.getBody()).path("response").path("body");
        JsonNode items = body.path("items").path("item");
        int totalCount = body.path("totalCount").asInt();

        for (JsonNode item : items) {
            int contentId = item.path("contentid").asInt();
            double rating = reviewService.getAverageRating(contentId);
            int bookmarkCount = bookmarkService.countByContentId(contentId);
            int reviewCount = reviewService.getTotalReviewCount(contentId);
            areaList.add(Map.of(
                    "contentid", String.valueOf(contentId),
                    "contenttypeid", item.path("contenttypeid").asText(),
                    "title", item.path("title").asText(),
                    "addr1", item.path("addr1").asText(),
                    "firstimage", item.path("firstimage").asText(),
                    "mapx", item.path("mapx").asText(),
                    "mapy", item.path("mapy").asText(),
                    "rating", String.format("%.1f", rating),
                    "bookmarkCount", String.valueOf(bookmarkCount),
                    "reviewCount", String.valueOf(reviewCount)
            ));
        }

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        return Map.of("list", areaList, "totalPages", totalPages);
    }

}

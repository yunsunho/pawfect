package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @GetMapping("/api/themeData")
    @ResponseBody
    public Map<String, Object> getThemeData(
            @RequestParam int contentTypeId,
            @RequestParam(defaultValue = "O") String arrange,
            @RequestParam(defaultValue = "1") int pageNo) throws Exception {

        if (!List.of("O", "review", "rating", "bookmark").contains(arrange)) {
            arrange = "O";
        }

        List<Map<String, Object>> themeList = new ArrayList<>();
        Set<Integer> addedContentIds = new HashSet<>();

        int pageSize = 20;
        int offset = (pageNo - 1) * pageSize;

        List<Map<String, Object>> sortedFromDb = new ArrayList<>();
        if ("review".equals(arrange)) {
            sortedFromDb = reviewService.getContentIdsSortedByReviewCountAndType(contentTypeId);
        } else if ("rating".equals(arrange)) {
            sortedFromDb = reviewService.getContentIdsSortedByAvgRatingAndType(contentTypeId);
        } else if ("bookmark".equals(arrange)) {
            sortedFromDb = bookmarkService.getContentIdsSortedByBookmarkCountAndType(contentTypeId);
        }

        int totalDb = sortedFromDb.size();
        int dbPageEnd = Math.min(offset + pageSize, totalDb);
        List<Map<String, Object>> pagedDbList = sortedFromDb.subList(Math.min(offset, totalDb), dbPageEnd);

        for (Map<String, Object> row : pagedDbList) {
            int contentId = (int) row.get("contentId");
            String title = (String) row.get("title");
            String addr1 = (String) row.get("addr1");
            String firstimage = (String) row.get("firstimage");
            Object mapXObj = row.get("mapX");
            Object mapYObj = row.get("mapY");

            Object ratingObj = row.get("avgRating");
            double rating = ratingObj != null ? Double.parseDouble(ratingObj.toString()) : reviewService.getAverageRating(contentId);

            Object reviewCountObj = row.get("reviewCount");
            int reviewCount = reviewCountObj != null ? Integer.parseInt(reviewCountObj.toString()) : reviewService.getTotalReviewCount(contentId);

            int bookmarkCount = bookmarkService.countByContentId(contentId);

            Map<String, Object> place = new HashMap<>();
            place.put("contentid", contentId);
            place.put("title", title != null ? title : "(제목 없음)");
            place.put("addr1", addr1 != null ? addr1 : "-");
            place.put("firstimage", firstimage != null ? firstimage : "");
            place.put("mapx", mapXObj != null ? String.valueOf(mapXObj) : "");
            place.put("mapy", mapYObj != null ? String.valueOf(mapYObj) : "");
            place.put("rating", String.format("%.1f", rating));
            place.put("reviewCount", reviewCount);
            place.put("bookmarkCount", bookmarkCount);
            place.put("contenttypeid", contentTypeId);

            themeList.add(place);
            addedContentIds.add(contentId);
        }

     // --- 2. API 데이터 가져오기 (중복되지 않는 것만 추가) ---
        if ("O".equals(arrange)) {
            String encoded = URLEncoder.encode(serviceKey, "UTF-8");
            String url = "https://apis.data.go.kr/B551011/KorPetTourService/petTourSyncList?"
                    + "serviceKey=" + encoded
                    + "&numOfRows=" + pageSize
                    + "&pageNo=" + pageNo
                    + "&MobileOS=ETC"
                    + "&MobileApp=PawfectTour"
                    + "&arrange=O"
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
            int totalCount = body.path("totalCount").asInt();  // <-- ✅ 여기가 전체 데이터 수

            for (JsonNode item : items) {
                int contentId = item.path("contentid").asInt();
                if (addedContentIds.contains(contentId)) continue;

                double rating = reviewService.getAverageRating(contentId);
                int bookmarkCount = bookmarkService.countByContentId(contentId);
                int reviewCount = reviewService.getTotalReviewCount(contentId);

                Map<String, Object> place = new HashMap<>();
                place.put("contentid", contentId);
                place.put("contenttypeid", item.path("contenttypeid").asText());
                place.put("title", item.path("title").asText());
                place.put("addr1", item.path("addr1").asText());
                place.put("firstimage", item.path("firstimage").asText());
                place.put("mapx", item.path("mapx").asText());
                place.put("mapy", item.path("mapy").asText());
                place.put("rating", String.format("%.1f", rating));
                place.put("bookmarkCount", bookmarkCount);
                place.put("reviewCount", reviewCount);

                themeList.add(place);
            }

            // ✅ totalCount를 기준으로 totalPages 계산
            int totalPages = (int) Math.ceil((double) totalCount / pageSize);

            return Map.of("list", themeList, "totalPages", totalPages);
        }


        int totalPages = (int) Math.ceil((double) sortedFromDb.size() / pageSize);
        return Map.of("list", themeList, "totalPages", totalPages);
    }
}

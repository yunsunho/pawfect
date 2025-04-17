package com.example.Pawfect.controller.travel;

import java.net.URI;
import java.net.URLEncoder;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.BookmarkDto;
import com.example.Pawfect.service.BookmarkService;
import com.example.Pawfect.service.ReviewService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DetailController {
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private BookmarkService bookmarkService;
	
    @Value("${api.service-key}")
    private String serviceKey;

    @GetMapping("/detail/{contentId}/{contentTypeId}")
    public String getDetailPage(
            @PathVariable String contentId,
            @PathVariable String contentTypeId,
            Model model,
            @AuthenticationPrincipal CustomUserDetails userDetails) 
            		throws Exception {

        // 로그인된 사용자 확인
        if (userDetails != null) {
            model.addAttribute("user", userDetails.getUser());  // 로그인된 사용자 정보 추가
        }

        String encodedKey = URLEncoder.encode(serviceKey, "UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();

        // ✅ 공통 상세 정보 (detailCommon)
        String commonUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailCommon?"
                + "serviceKey=" + encodedKey
                + "&MobileOS=ETC&MobileApp=Pawfect"
                + "&contentId=" + contentId
                + "&defaultYN=Y&firstImageYN=Y&areacodeYN=N&catcodeYN=N"
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
            detailCommon.put("firstimage", common.path("firstimage").asText());

            model.addAttribute("common", detailCommon);
            
            model.addAttribute("title", common.path("title").asText());
            model.addAttribute("firstimage", common.path("firstimage").asText());
            model.addAttribute("addr1", common.path("addr1").asText());
            model.addAttribute("mapX", common.path("mapx").asText());
            model.addAttribute("mapY", common.path("mapy").asText());
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
        //petTour.put("acmpyTypeCd", petNode.path("acmpyTypeCd").asText());
        petTour.put("relaPosesFclty", petNode.path("relaPosesFclty").asText());
        petTour.put("relaFrnshPrdlst", petNode.path("relaFrnshPrdlst").asText());
        //petTour.put("etcAcmpyInfo", petNode.path("etcAcmpyInfo").asText().replaceAll("\n", "<br>"));
        petTour.put("relaPurcPrdlst", petNode.path("relaPurcPrdlst").asText());
        //petTour.put("acmpyPsblCpam", petNode.path("acmpyPsblCpam").asText());
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

        JsonNode introNode = mapper.readTree(introResponse.getBody())
                .path("response").path("body").path("items").path("item").get(0);
        
        Map<String, String> detailIntro = new HashMap<>();

        switch (contentTypeId) {
            case "12": // 관광지
            	detailIntro.put("accomcount", introNode.path("accomcount").asText());
            	detailIntro.put("chkcreditcard", introNode.path("chkcreditcard").asText());
            	detailIntro.put("expagerange", introNode.path("expagerange").asText());
            	detailIntro.put("expguide", introNode.path("expguide").asText());
            	detailIntro.put("heritage1", introNode.path("heritage1").asText());
            	detailIntro.put("heritage2", introNode.path("heritage2").asText());
            	detailIntro.put("heritage3", introNode.path("heritage3").asText());
            	detailIntro.put("infocenter", introNode.path("infocenter").asText());
            	detailIntro.put("opendate", introNode.path("opendate").asText());
            	detailIntro.put("parking", introNode.path("parking").asText());
            	detailIntro.put("restdate", introNode.path("restdate").asText());
            	detailIntro.put("useseason", introNode.path("useseason").asText());
            	detailIntro.put("usetime", introNode.path("usetime").asText());
                break;
            case "14": // 문화시설
            	detailIntro.put("accomcountculture", introNode.path("accomcountculture").asText());
            	detailIntro.put("chkcreditcardculture", introNode.path("chkcreditcardculture").asText());
            	detailIntro.put("discountinfo", introNode.path("discountinfo").asText());
            	detailIntro.put("infocenterculture", introNode.path("infocenterculture").asText());
            	detailIntro.put("parkingculture", introNode.path("parkingculture").asText());
            	detailIntro.put("parkingfee", introNode.path("parkingfee").asText());
            	detailIntro.put("restdateculture", introNode.path("restdateculture").asText());
            	detailIntro.put("usefee", introNode.path("usefee").asText());
            	detailIntro.put("usetimeculture", introNode.path("usetimeculture").asText());
            	detailIntro.put("scale", introNode.path("scale").asText());
            	detailIntro.put("spendtime", introNode.path("spendtime").asText());
            	break;
            case "15": // 행사/공연/축제
            	detailIntro.put("agelimit", introNode.path("agelimit").asText());
            	detailIntro.put("bookingplace", introNode.path("bookingplace").asText());
            	detailIntro.put("discountinfofestival", introNode.path("discountinfofestival").asText());
            	detailIntro.put("eventenddate", introNode.path("eventenddate").asText());
            	detailIntro.put("eventhomepage", introNode.path("eventhomepage").asText());
            	detailIntro.put("eventplace", introNode.path("eventplace").asText());
            	detailIntro.put("eventstartdate", introNode.path("eventstartdate").asText());
            	detailIntro.put("festivalgrade", introNode.path("festivalgrade").asText());
            	detailIntro.put("placeinfo", introNode.path("placeinfo").asText());
            	detailIntro.put("playtime", introNode.path("playtime").asText());
            	detailIntro.put("program", introNode.path("program").asText());
            	detailIntro.put("spendtimefestival", introNode.path("spendtimefestival").asText());
            	detailIntro.put("sponsor1", introNode.path("sponsor1").asText());
            	detailIntro.put("sponsor1tel", introNode.path("sponsor1tel").asText());
            	detailIntro.put("sponsor2", introNode.path("sponsor2").asText());
            	detailIntro.put("sponsor2tel", introNode.path("sponsor2tel").asText());
            	detailIntro.put("subevent", introNode.path("subevent").asText());
            	detailIntro.put("usetimefestival", introNode.path("usetimefestival").asText());
                break;
            case "28": // 레포츠
            	detailIntro.put("accomcountleports", introNode.path("accomcountleports").asText());
            	detailIntro.put("chkcreditcardleports", introNode.path("chkcreditcardleports").asText());
            	detailIntro.put("expagerangeleports", introNode.path("expagerangeleports").asText());
            	detailIntro.put("infocenterleports", introNode.path("infocenterleports").asText());
            	detailIntro.put("openperiod", introNode.path("openperiod").asText());
            	detailIntro.put("parkingfeeleports", introNode.path("parkingfeeleports").asText());
            	detailIntro.put("parkingleports", introNode.path("parkingleports").asText());
            	detailIntro.put("reservation", introNode.path("reservation").asText());
            	detailIntro.put("restdateleports", introNode.path("restdateleports	").asText());
            	detailIntro.put("scaleleports", introNode.path("scaleleports").asText());
            	detailIntro.put("usefeeleports", introNode.path("usefeeleports").asText());
            	detailIntro.put("usetimeleports", introNode.path("usetimeleports").asText());
            	break;
            case "32": // 숙박
            	detailIntro.put("accomcountlodging", introNode.path("accomcountlodging").asText());
            	detailIntro.put("benikia", introNode.path("benikia").asText());
            	detailIntro.put("checkintime", introNode.path("checkintime").asText());
            	detailIntro.put("checkouttime", introNode.path("checkouttime").asText());
            	detailIntro.put("chkcooking", introNode.path("chkcooking").asText());
            	detailIntro.put("foodplace", introNode.path("foodplace").asText());
            	detailIntro.put("goodstay", introNode.path("goodstay").asText());
            	detailIntro.put("hanok", introNode.path("hanok").asText());
            	detailIntro.put("infocenterlodging", introNode.path("infocenterlodging").asText());
            	detailIntro.put("parkinglodging", introNode.path("parkinglodging").asText());
            	detailIntro.put("pickup", introNode.path("pickup").asText());
            	detailIntro.put("roomcount", introNode.path("roomcount").asText());
            	detailIntro.put("reservationlodging", introNode.path("reservationlodging").asText());
            	detailIntro.put("reservationurl", introNode.path("reservationurl").asText());
            	detailIntro.put("roomtype", introNode.path("roomtype").asText());
            	detailIntro.put("scalelodging", introNode.path("scalelodging").asText());
            	detailIntro.put("subfacility", introNode.path("subfacility").asText());
            	detailIntro.put("barbecue", introNode.path("barbecue").asText());
            	detailIntro.put("beauty", introNode.path("beauty").asText());
            	detailIntro.put("beverage", introNode.path("beverage").asText());
            	detailIntro.put("bicycle", introNode.path("bicycle").asText());
            	detailIntro.put("campfire", introNode.path("campfire").asText());
            	detailIntro.put("fitness", introNode.path("fitness").asText());
            	detailIntro.put("karaoke", introNode.path("karaoke").asText());
            	detailIntro.put("publicbath", introNode.path("publicbath").asText());
            	detailIntro.put("publicpc", introNode.path("publicpc").asText());
            	detailIntro.put("sauna", introNode.path("sauna").asText());
            	detailIntro.put("seminar", introNode.path("seminar").asText());
            	detailIntro.put("sports", introNode.path("sports").asText());
            	detailIntro.put("refundregulation", introNode.path("refundregulation").asText());
            	break;
            case "38": // 쇼핑
            	detailIntro.put("chkcreditcardshopping", introNode.path("chkcreditcardshopping").asText());
            	detailIntro.put("culturecenter", introNode.path("culturecenter").asText());
            	detailIntro.put("fairday", introNode.path("fairday").asText());
            	detailIntro.put("infocentershopping", introNode.path("infocentershopping").asText());
            	detailIntro.put("opendateshopping", introNode.path("opendateshopping").asText());
            	detailIntro.put("opentime", introNode.path("opentime").asText());
            	detailIntro.put("parkingshopping", introNode.path("parkingshopping").asText());
            	detailIntro.put("restdateshopping", introNode.path("restdateshopping").asText());
            	detailIntro.put("restroom", introNode.path("restroom").asText());
            	detailIntro.put("saleitem", introNode.path("saleitem").asText());
            	detailIntro.put("saleitemcost", introNode.path("saleitemcost").asText());
            	detailIntro.put("scaleshopping", introNode.path("scaleshopping").asText());
            	detailIntro.put("shopguide", introNode.path("shopguide").asText());
            	break;
            case "39": // 음식점
            	detailIntro.put("chkcreditcardfood", introNode.path("chkcreditcardfood").asText());
            	detailIntro.put("discountinfofood", introNode.path("discountinfofood").asText());
            	detailIntro.put("firstmenu", introNode.path("firstmenu").asText());
            	detailIntro.put("infocenterfood", introNode.path("infocenterfood").asText());
            	detailIntro.put("kidsfacility", introNode.path("kidsfacility").asText());
            	detailIntro.put("opendatefood", introNode.path("opendatefood").asText());
            	detailIntro.put("opentimefood", introNode.path("opentimefood").asText());
            	detailIntro.put("packing", introNode.path("packing").asText());
            	detailIntro.put("parkingfood", introNode.path("parkingfood").asText());
            	detailIntro.put("reservationfood", introNode.path("reservationfood").asText());
            	detailIntro.put("restdatefood", introNode.path("restdatefood").asText());
            	detailIntro.put("scalefood", introNode.path("scalefood").asText());
            	detailIntro.put("seat", introNode.path("seat").asText());
            	detailIntro.put("smoking", introNode.path("smoking").asText());
            	detailIntro.put("treatmenu", introNode.path("treatmenu").asText());
            	break;
            default:
            	detailIntro.put("etc", "해당 타입의 상세정보 없음");
                break;
        }
        
     // ✅ detailInfo API 호출
        String infoUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailInfo?"
                + "serviceKey=" + encodedKey
                + "&MobileOS=ETC&MobileApp=Pawfect"
                + "&contentId=" + contentId
                + "&contentTypeId=" + contentTypeId
                + "&_type=json";

        ResponseEntity<String> infoResponse = restTemplate.exchange(
                new URI(infoUrl), HttpMethod.GET, new HttpEntity<>(new HttpHeaders()), String.class);

        JsonNode infoItems = mapper.readTree(infoResponse.getBody())
                .path("response").path("body").path("items").path("item");

        if (contentTypeId.equals("32")) {
            String detailInfoUrl = "https://apis.data.go.kr/B551011/KorPetTourService/detailInfo?"
                    + "serviceKey=" + encodedKey
                    + "&MobileOS=ETC&MobileApp=Pawfect"
                    + "&contentId=" + contentId
                    + "&contentTypeId=32"
                    + "&_type=json"
                    + "&numOfRows=100&pageNo=1"; // 최대 100개까지 받아오기

            ResponseEntity<String> roomResponse = restTemplate.exchange(
                    new URI(detailInfoUrl),
                    HttpMethod.GET,
                    new HttpEntity<>(new HttpHeaders()),
                    String.class
            );

            JsonNode items = mapper.readTree(roomResponse.getBody())
                    .path("response").path("body").path("items").path("item");

            List<Map<String, Object>> rooms = new ArrayList<>();

            for (JsonNode room : items) {
                Map<String, Object> roomInfo = new HashMap<>();

                // 기본 정보
                roomInfo.put("title", room.path("roomtitle").asText());
                roomInfo.put("roomsize", room.path("roomsize2").asText() + "㎡");
                roomInfo.put("basecount", room.path("roombasecount").asText());
                roomInfo.put("maxcount", room.path("roommaxcount").asText());
                roomInfo.put("offmin", room.path("roomoffseasonminfee1").asText());
                roomInfo.put("offmax", room.path("roomoffseasonminfee2").asText());
                roomInfo.put("peakmin", room.path("roompeakseasonminfee1").asText());
                roomInfo.put("peakmax", room.path("roompeakseasonminfee2").asText());

                // 제공물품
                List<String> providedItems = new ArrayList<>();
                if ("Y".equals(room.path("roombathfacility").asText())) providedItems.add("목욕시설");
                if ("Y".equals(room.path("roomaircondition").asText())) providedItems.add("에어컨");
                if ("Y".equals(room.path("roomtv").asText())) providedItems.add("TV");
                if ("Y".equals(room.path("roomrefrigerator").asText())) providedItems.add("냉장고");
                if ("Y".equals(room.path("roominternet").asText())) providedItems.add("인터넷");
                if ("Y".equals(room.path("roomtoiletries").asText())) providedItems.add("취사용품");
                if ("Y".equals(room.path("roomhairdryer").asText())) providedItems.add("헤어드라이기");
                roomInfo.put("amenities", String.join(",", providedItems));

                // 이미지 리스트
                List<String> imageList = new ArrayList<>();
                for (int i = 1; i <= 5; i++) {
                    String url = room.path("roomimg" + i).asText();
                    if (url != null && !url.isEmpty()) {
                        imageList.add(url);
                    }
                }
                roomInfo.put("images", imageList); // List<String>

                rooms.add(roomInfo);
            }
            model.addAttribute("roomList", rooms);

        } else {
            // contentTypeId가 32가 아닐 때는 일반 infoname/infotext 출력
            List<Map<String, String>> introList = new ArrayList<>();

            for (JsonNode node : infoItems) {
                String name = node.path("infoname").asText();
                String text = node.path("infotext").asText();

                if (!text.equals("0") && !text.equals("없음") && !text.isBlank()) {
                    introList.add(Map.of("name", name, "text", text));
                }
            }

            model.addAttribute("introList", introList);
        }

        
        model.addAttribute("Intro", detailIntro);
        
        int contentIdInt = Integer.parseInt(contentId);

        double averageRating = reviewService.getAverageRating(contentIdInt);
        int reviewCount = reviewService.getTotalReviewCount(contentIdInt);
        int bookmarkCount = bookmarkService.getBookmarkCount(contentIdInt);

        boolean isBookmarked = false;
        if (userDetails != null) {
            BookmarkDto dto = new BookmarkDto();
            dto.setUserId(userDetails.getUser().getUserId());
            dto.setContentId(contentIdInt);
            isBookmarked = bookmarkService.isBookmarked(dto);
        }

        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviewCount", reviewCount);
        model.addAttribute("bookmarkCount", bookmarkCount);
        model.addAttribute("isBookmarked", isBookmarked);

        
        // 페이지 렌더링
        return "travel/detail";
    }
}

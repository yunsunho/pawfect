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

        model.addAttribute("Intro", detailIntro);

        
        // 페이지 렌더링
        return "travel/detail";
    }
}

package com.example.Pawfect.controller.travel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.service.ReviewService;

@Controller
@RequestMapping("/travel")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/reviewWrite")
    public String writeReview(@RequestParam int contentId,
    						  @RequestParam int contentTypeId,
                              @RequestParam String reviewContent,
                              @RequestParam int reviewRating,
                              @RequestParam("reviewImages") List<MultipartFile> reviewImages,
                              @RequestParam String firstimage,
                              @RequestParam String addr1,
                              @RequestParam float mapX,
                              @RequestParam float mapY,
                              @RequestParam String title,
                              @RequestParam String areaCode,
                              @RequestParam String sigunguCode,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 확인
        if (userDetails == null) {
            return "redirect:/loginForm";
        }
        
        // 리뷰 저장
        int reviewId = reviewService.saveReview(contentId, userDetails.getUser().getUserId(), reviewContent, reviewRating, title, contentTypeId, firstimage, addr1, mapX, mapY, areaCode, sigunguCode);

        // 이미지 저장
        int imageOrder = 1;
        for (MultipartFile image : reviewImages) {
            if (!image.isEmpty()) {
                String imagePath = saveImage(image);
                if (imagePath != null && !imagePath.isBlank()) {
                    reviewService.saveReviewImageWithOrder(reviewId, imagePath, imageOrder++);
                }
            }
        }

        return "redirect:/detail/" + contentId + "/" + contentTypeId;
    }



    private String saveImage(MultipartFile image) {
        try {
            // 저장할 폴더 경로 지정
            String uploadDir = System.getProperty("user.dir") + "/upload/review/";  // 실제 경로로 수정 필요
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();  // 디렉토리 없으면 생성
            }

            // 파일명 생성 (UUID + 확장자)
            String fileExtension = getFileExtension(image);  // 확장자 추출
            if (fileExtension == null) {
                throw new IOException("파일 확장자를 알 수 없습니다.");
            }

            // 고유한 파일명 생성 (UUID + 확장자)
            String fileName = UUID.randomUUID().toString() + "." + fileExtension;
            String filePath = uploadDir + fileName;

            // 이미지 파일 저장
            image.transferTo(new File(filePath));  // 파일 시스템에 저장

            // 저장된 경로를 반환 (상대 경로를 DB에 저장)
            return "/upload/review/" + fileName;  // 상대 경로를 반환
        } catch (IOException e) {
            e.printStackTrace();
            return null;  // 오류 발생 시 null 반환
        }
    }



    // 파일 확장자 추출 (jpg, png, gif 등)
    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        }
        return null; // 확장자가 없는 경우 null 반환
    }
    
    @DeleteMapping("/review/delete/{reviewId}")
    @ResponseBody
    public ResponseEntity<String> deleteReview(@PathVariable int reviewId,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        String loginUserId = userDetails.getUser().getUserId(); // 로그인한 사용자
        String reviewOwnerId = reviewService.getReviewOwner(reviewId); // 리뷰 작성자

        if (!loginUserId.equals(reviewOwnerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("작성자만 삭제할 수 있습니다.");
        }

        reviewService.deleteReviewById(reviewId); // 삭제 실행
        return ResponseEntity.ok("삭제 완료");
    }

    
    @GetMapping("/reviews/{contentId}")
    public String getReviewsFragment(@PathVariable int contentId,
                                     @RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "5") int pageSize,
                                     Model model) {

        int offset = (page - 1) * pageSize;

        List<ReviewDto> reviews = reviewService.getPagedReviewsWithUser(contentId, offset, pageSize);
        int totalCount = reviewService.getTotalReviewCount(contentId);
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        model.addAttribute("reviews", reviews);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "travel/reviewList"; // 이 JSP에서 페이지네이션을 표현해야 함
    }


}





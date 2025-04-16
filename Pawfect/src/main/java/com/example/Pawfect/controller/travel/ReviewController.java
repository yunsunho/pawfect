package com.example.Pawfect.controller.travel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
                              @RequestParam String reviewContent,
                              @RequestParam int reviewRating,
                              @RequestParam("reviewImages") List<MultipartFile> reviewImages,
                              @RequestParam String contentTypeId,
                              @AuthenticationPrincipal CustomUserDetails userDetails) {

        // 로그인된 사용자 확인
        if (userDetails == null) {
            return "redirect:/loginForm";
        }

        // 리뷰 저장
        int reviewId = reviewService.saveReview(contentId, userDetails.getUser().getUserId(), reviewContent, reviewRating);

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
    
    @GetMapping("/detail/{contentId}")
    public String getDetailPage(@PathVariable int contentId, Model model) {
        // 해당 contentId에 대한 리뷰와 이미지 경로 불러오기
        List<String> reviewImages = reviewService.getReviewImagesByOrder(contentId);  // 이미지 순서대로 불러오기
        model.addAttribute("reviewImages", reviewImages);  // 이미지 경로를 모델에 추가

        // 리뷰 내용 불러오기
        List<ReviewDto> reviews = reviewService.getReviewsByContentId(contentId);
        model.addAttribute("reviews", reviews);

        return "travel/detail";  // detail.jsp로 이동
    }

}





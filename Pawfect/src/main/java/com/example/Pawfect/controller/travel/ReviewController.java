package com.example.Pawfect.controller.travel;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.Pawfect.dto.ReviewDto;
import com.example.Pawfect.dto.ReviewImagesDto;
import com.example.Pawfect.service.FileStorageService;
import com.example.Pawfect.service.ReviewService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService; 
    private final FileStorageService fileStorageService;

    @GetMapping("/travel/reviewWrite")
    public String submitReview(@RequestParam String reviewContent,
                               @RequestParam int reviewRating,
                               @RequestParam int contentId,
                               @RequestParam int contentTypeId,
                               @RequestParam("images") List<MultipartFile> images,
                               Principal principal) throws IOException {

        String userId = principal.getName();

        ReviewDto reviewDTO = new ReviewDto();
        reviewDTO.setContentId(contentId);
        reviewDTO.setUserId(userId);
        reviewDTO.setReviewRating(reviewRating);
        reviewDTO.setReviewContent(reviewContent);

        int reviewId = reviewService.saveReview(reviewDTO);

        // 이미지 저장
        List<ReviewImagesDto> imageList = new ArrayList<>();
        int order = 0;
        for (MultipartFile file : images) {
            if (!file.isEmpty()) {
                String uploadPath = fileStorageService.save(file); // 파일 저장
                ReviewImagesDto image = new ReviewImagesDto();
                image.setReviewId(reviewId); // ✅ FK 지정
                image.setReviewImage(uploadPath);
                image.setReviewImageOrder(order++);
                imageList.add(image);
            }
        }

        reviewService.saveReviewImages(reviewId, imageList);

        return "redirect:/detail/" + contentId + "/" + contentTypeId;
    }


}

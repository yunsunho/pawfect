package com.example.Pawfect.controller.mypage;

import com.example.Pawfect.auth.CustomUserDetails;
import com.example.Pawfect.dto.ProfileUpdateDto;
import com.example.Pawfect.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class ProfileController {
	private final MyPageService myPageService;

	// 프로필 정보 수정 (닉네임, 반려동물 정보)
	@PostMapping("/profile")
	@ResponseBody
	public String updateProfile(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody ProfileUpdateDto profileDto) {

		String userId = userDetails.getUser().getUserId();
		profileDto.setUserId(userId);

		if (profileDto.isNicknameChanged()) {
			return myPageService.updateProfile(profileDto) ? "success" : "fail";
		} else {
			return myPageService.updatePetOnly(profileDto) ? "success" : "fail";
		}
	}

	// 프로필 이미지 수정
	@PostMapping("/profile/image")
	@ResponseBody
	public String updateProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestParam("profileImage") MultipartFile file) {

		if (file.isEmpty())
			return "fail";

		String userId = userDetails.getUser().getUserId();

		// 파일 저장할 실제 경로
		String uploadDir = "/images/upload/profile/";
		String realPath = new File("src/main/resources/static" + uploadDir).getAbsolutePath();

		try {
			// UUID + 원본 파일명으로 고유 파일명 생성
			String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
			File saveFile = new File(realPath, filename);

			// 해당 디렉토리가 없으면 생성
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}
			// 파일 저장
			file.transferTo(saveFile);

			// DB에 상대 경로 저장
			String dbPath = uploadDir + filename;
			myPageService.updateProfileImage(userId, dbPath);

			return "success";

		} catch (IOException e) {
			e.printStackTrace();
			return "fail";
		}
	}
}

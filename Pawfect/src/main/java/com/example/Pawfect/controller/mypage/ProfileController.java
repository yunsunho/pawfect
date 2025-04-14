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
import java.util.Map;
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
			return "";

		String uploadDir = System.getProperty("user.dir") + "/upload/profile/";
		String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
		File saveFile = new File(uploadDir, filename);

		try {
			if (!saveFile.getParentFile().exists()) {
				saveFile.getParentFile().mkdirs();
			}

			file.transferTo(saveFile);
			String dbPath = "/profile-img/" + filename;

			return dbPath;
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	// 프로필 이미지 삭제
	@PostMapping("/profile/image/delete")
	@ResponseBody
	public String deleteProfileImage(@AuthenticationPrincipal CustomUserDetails userDetails) {
		String userId = userDetails.getUser().getUserId();

		boolean result = myPageService.deleteProfileImage(userId);
		return result ? "success" : "fail";
	}

	// 프로필 이미지 저장
	@PostMapping("/profile/image/save")
	@ResponseBody
	public String saveProfileImagePath(@AuthenticationPrincipal CustomUserDetails userDetails,
			@RequestBody Map<String, String> body) {
		String userId = userDetails.getUser().getUserId();
		String imagePath = body.get("imagePath");

		boolean result = myPageService.updateProfileImage(userId, imagePath);
		return result ? "success" : "fail";
	}
}

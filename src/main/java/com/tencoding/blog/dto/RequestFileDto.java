package com.tencoding.blog.dto;

import org.springframework.web.multipart.MultipartFile;

import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestFileDto {
	
//	private MultipartFile file[]; -> 여러개의 파일을 받아야 하는 경우 배열로 받음
	private MultipartFile file; // 키 값 반드시 jsp파일에서 name 값과 똑같아야한다.
	private String uuid; // 고유한 파일이름을 만들기 위한 속성. 같은 이름의 파일 존재할 때 필요함
	private String storyText;
	
	public Image toEntity(String storyImageUrl, User user) {
		return Image.builder()
				.storyText(storyText)
				.storyImageUrl(storyImageUrl)
				.originFileName(file.getOriginalFilename())
				.user(user)
				.build();
	}
	
}

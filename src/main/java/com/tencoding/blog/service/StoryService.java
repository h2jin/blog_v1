package com.tencoding.blog.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tencoding.blog.dto.RequestFileDto;
import com.tencoding.blog.model.Image;
import com.tencoding.blog.model.User;
import com.tencoding.blog.repository.StoryRepository;

@Service
public class StoryService {
	
	@Value("${file.path}")
	private String uploadFolder;
	
	@Autowired
	private StoryRepository storyRepository;
	
	
	public Page<Image> getImageList(Pageable pageable) {
		return storyRepository.findAll(pageable);
	}
	
	
	@Transactional
	public void imageUpload(RequestFileDto fileDto, User user) {
		// 파일 업로드 기능이기 때문에 해당 서버에 바이너리를 받아서 파일을 생성하고
		// 성공하면 DB에 저장 (실패한 경우 DB에 넣을 필요 없음. 데이터의 무결성)
		// 중복 방지 로직 (대표적으로 uuid/시간 사용)
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + "story";
		// 서버 컴퓨터의 path를 가지고 와야한다. (경로)
		String newFileName = (imageFileName.trim()).replaceAll("\\s", "");
		
		Path imageFilePath = Paths.get(uploadFolder + newFileName);
		
		try {
			Files.write(imageFilePath, fileDto.getFile().getBytes());
			// DB 저장
			storyRepository.save(fileDto.toEntity(newFileName, user));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
}

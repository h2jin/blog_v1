package com.tencoding.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Properties {
	private String nickname;
	private String profileImage;
	private String thumbnailImage;

}

package com.tencoding.blog.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KakaoAccount {
	private boolean profileNicknameNeedsAgreement;
	private boolean profileImageNeedsAgreement;
	private Profile profile;
	private boolean hasEmail;
	private boolean emailNeedsAgreement;
	private boolean isEmailValid;
	private boolean isEmailVerified;
	private String email;
}

package com.tencoding.blog.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
	private String statusCode;
	private String requestUrl;
	private int code;
	private String message;
	private List<CustomError> errorList;

}

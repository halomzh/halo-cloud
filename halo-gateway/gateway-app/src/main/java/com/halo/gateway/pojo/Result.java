package com.halo.gateway.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author shoufeng
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> implements Serializable {

	private int code;

	private T data;

	private String message;

	public static <T> Result<T> newFailResult(String message) {

		return new Result<>(500, null, message);
	}

	public static <T> Result<T> newFailResult(String message, T data) {

		return new Result<>(500, data, message);
	}

	public static <T> Result<T> newFailResult(int code, String message, T data) {

		return new Result<>(code, data, message);
	}

}

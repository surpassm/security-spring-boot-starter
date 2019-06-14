package com.github.surpassm.common.jackson;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.surpassm.common.constant.Constant;
import lombok.Builder;
import lombok.Data;

/**
 * 前端JSON返回格式,自定义响应格式
 *
 * @author mc
 * version 1.0
 * date 2018/10/30 12:52
 * description
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T>{
	/**
	 * 响应业务状态
	 */
	private Integer code;

	/**
	 * 响应消息
	 */
	private String message;

	/**
	 * 响应中的数据
	 */
	private T data;

	public Result(T data) {
		this.code = Constant.SUCCESS_CODE;
		this.message = Constant.SUCCESS_MSG;
		this.data = data;
	}

	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(Integer code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public static Result ok(Object data) {
		return new Result(data);
	}


	public static Result ok() {
		return new Result("");
	}
	public static Result fail() {
		return new Result(Constant.FAIL_CODE, Constant.FAIL_MSG);
	}

	public static Result fail(String msg) {
		return new Result(Constant.FAIL_CODE, msg);
	}

	public static Result fail(Integer code, String msg) {
		return new Result(code, msg);
	}

	public static Result fail(Object data) {
		return new Result(Constant.FAIL_CODE, Constant.FAIL_MSG, data);
	}




	public static Result ok(ResultCode code) {
		return new Result(code.getCode(),code.getMsg());
	}
	public static <T> Result<T> ok(ResultCode code,T t) {
		return new Result(code.getCode(),code.getMsg(),t);
	}
	public static Result fail(ResultCode code) {
		return new Result(code.getCode(), code.getMsg());
	}
	public static <T> Result<T> fail(ResultCode code,T t) {
		return new Result(code.getCode(),code.getMsg(),t);
	}

}

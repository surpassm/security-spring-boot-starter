package com.github.surpassm.common.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class Result{
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
	private Object data="";

	public Result(Object data) {
		this.code = ResultCode.SUCCESS.getCode();
		this.message = ResultCode.SUCCESS.getMsg();
		this.data = data;
	}

	public Result(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	public Result(Integer code, String message, Object data) {
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
		return new Result(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg());
	}

	public static Result fail(String msg) {
		return new Result(ResultCode.ERROR.getCode(), msg);
	}

	public static Result fail(Integer code, String msg) {
		return new Result(code, msg);
	}



	public static Result fail(Object data) {
		return new Result(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMsg(), data);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}


}

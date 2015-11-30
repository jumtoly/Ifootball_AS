package com.ifootball.app.entity;

import java.io.Serializable;

import com.neweggcn.lib.json.annotations.SerializedName;

public class ResultData<T> implements Serializable {
	private static final long serialVersionUID = -2822558985195026018L;
	/**
	 * 获取或设置消息编号
	 */
	@SerializedName("Code")
	private int Code;
	/**
	 * 获取或设置消息描述
	 */
	@SerializedName("Message")
	private String Message;
	
	@SerializedName("Success")
	private boolean Success;
	/**
	 * 获取或设置相关附加数据
	 */
	@SerializedName("Data")
	private T Data;
	
	/**
	 * 获取消息编号
	 */
	public int getCode() {
		return Code;
	}
	/**
	 * 设置消息编号
	 */
	public void setCode(int code) {
		Code = code;
	}
	/**
	 * 获取消息描述
	 */
	public String getMessage() {
		return Message;
	}
	/**
	 * 设置消息描述
	 */
	public void setMessage(String message) {
		Message = message;
	}
	/**
	 * 获取相关附加数据
	 */
	public T getData() {
		return Data;
	}
	/**
	 * 设置相关附加数据
	 */
	public void setData(T data) {
		Data = data;
	}
	public boolean isSuccess() {
		return Success;
	}
	public void setSuccess(boolean success) {
		Success = success;
	}
	
	
}

package com.ifootball.app.webservice;

/**
 * 自定义的异常，包装HTTP的Response Code，判断是服务端还是客户端异常。
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = -3693672283333003372L;

	private int responseCode;

	public ServiceException(int responseCode) {
		super();
		this.responseCode = responseCode;
	}

	/**
	 * 获取HTTP的Response Code。
	 * 
	 * @return HTTP的Response Code
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * 获取当前是否是服务端错误。
	 * 
	 * @return {@code true}如果是5XX，反之{@code false}；
	 */
	public boolean isServerError() {
		return responseCode >= 500 && responseCode < 600;
	}

	/**
	 * 获取当前是否是客户端错误。
	 * 
	 * @return {@code true}如果是4XX，反之{@code false}；
	 */
	public boolean isClientError() {
		return responseCode >= 400 && responseCode < 500;
	}
}
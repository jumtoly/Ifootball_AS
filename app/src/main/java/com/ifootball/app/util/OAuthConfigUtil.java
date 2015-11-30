package com.ifootball.app.util;

public class OAuthConfigUtil {
	private static OAuthConfigUtil instance;

	// Activity之间传递值时的KEY
	public static final String OAUTH_VERIFIER_URL = "oauth_verifier_url";

	private String curWeibo = "";
	private String appKey = "";
	private String appSecret = "";
	private String request_token_url = "";
	private String authoriz_token_url = "";
	private String access_token_url = "";
	private String callBackUrl = "";

	public static final String SINAW = "sina";
	public static final String QQW = "qq";

	// --------------------qq
	private final String qq_AppKey = "801213517";
	private final String qq_AppSecret = "9819935c0ad171df934d0ffb340a3c2d";
	private final String qq_Authoriz_token_url = "https://open.t.qq.com/cgi-bin/oauth2/authorize";
	private final String qq_Access_token_url = "https://open.t.qq.com/cgi-bin/oauth2/access_token";
	private final String qq_Callback_Url = "http://secure.ciotour.com";

	// ---------------------sina
	private final String sina_AppKey = "3498860661";
	private final String sina_AppSecret = "ae6a7317a03988ee21d5da9257b5cca3";
	private final String sina_Authorize_url = "https://api.weibo.com/oauth2/authorize";
	private final String sina_Access_token_url = "https://api.weibo.com/oauth2/access_token";
	private final String sina_Callback_Url = "http://www.oysd.cn";

	public static synchronized OAuthConfigUtil getInstance() {
		if (instance == null) {
			instance = new OAuthConfigUtil();
		}
		return instance;
	}

	public OAuthConfigUtil() {
		
	}

	/**
	 * 初始化QQ认证信息
	 */
	public void initQqData() {
		setAppKey(qq_AppKey);
		setAppSecret(qq_AppSecret);
		setAuthoriz_token_url(qq_Authoriz_token_url);
		setAccess_token_url(qq_Access_token_url);
		setCallBackUrl(qq_Callback_Url);
		setCurWeibo(QQW);
	}

	/**
	 * 初始化SINA认证信息
	 */
	public void initSinaData() {
		setAppKey(sina_AppKey);
		setAppSecret(sina_AppSecret);
		setAuthoriz_token_url(sina_Authorize_url);
		setAccess_token_url(sina_Access_token_url);
		setCallBackUrl(sina_Callback_Url);
		setCurWeibo(SINAW);
	}

	public String getCurWeibo() {
		return curWeibo;
	}

	/**
	 * 设置当前操作的weibo 不同的weibo请求存在着差异
	 * 
	 * @param curWeibo
	 */
	public void setCurWeibo(String curWeibo) {
		this.curWeibo = curWeibo;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getRequest_token_url() {
		return request_token_url;
	}

	public void setRequest_token_url(String requestTokenUrl) {
		request_token_url = requestTokenUrl;
	}

	public String getAuthoriz_token_url() {
		return authoriz_token_url;
	}

	public void setAuthoriz_token_url(String authorizTokenUrl) {
		authoriz_token_url = authorizTokenUrl;
	}

	public String getAccess_token_url() {
		return access_token_url;
	}

	public void setAccess_token_url(String accessTokenUrl) {
		access_token_url = accessTokenUrl;
	}

	public String getCallBackUrl() {
		return callBackUrl;
	}

	public void setCallBackUrl(String callback) {
		callBackUrl = callback;
	}
}

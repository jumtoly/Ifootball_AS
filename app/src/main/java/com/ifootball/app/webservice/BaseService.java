package com.ifootball.app.webservice;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import com.ifootball.app.framework.http.BetterHttp;
import com.ifootball.app.framework.http.BetterHttpRequest;
import com.ifootball.app.framework.http.BetterHttpResponse;
import com.ifootball.app.framework.http.JsonEntity;
import com.ifootball.app.util.CustomerUtil;
import com.ifootball.app.util.ForgotPasswordUtil;
import com.ifootball.app.util.StringUtil;

public class BaseService
{

	private static final String XNEWEGGAPPID_KEY = "x-newegg-app-id";
	private static final String XNEWEGGAPPID_VALUE = "i-tiqiu.com";
	private static final String REQUEST_COOKIE_KEY="Cookie";
	private static final String RESPONSE_COOKIE_KEY="Set-Cookie";


	public static final int EnvironmentType_LANTestRelease = 0;
	public static final int EnvironmentType_WWWTestRelease = 1;
	public static final int EnvironmentType_ProductionRelease = 2;

	public static final int CURRENTEN_ENVIRONMENT_TYPE = EnvironmentType_LANTestRelease;
	protected static final String URL_EXTENSION = ".egg";
	public static String RESTFUL_SERVICE_HOST_WWW = null;
	public static String RESTFUL_SERVICE_HOST_SSL = null;

	static
	{
		switch (CURRENTEN_ENVIRONMENT_TYPE)
		{

		case EnvironmentType_LANTestRelease:
			RESTFUL_SERVICE_HOST_WWW = "http://192.168.60.101:9100/";
			RESTFUL_SERVICE_HOST_SSL = "http://192.168.60.101:9100/";
			break;
		case EnvironmentType_WWWTestRelease:
			RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com.pre/";
			RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com.pre/";
			break;
		case EnvironmentType_ProductionRelease:
			RESTFUL_SERVICE_HOST_WWW = "http://app.kjt.com/";
			RESTFUL_SERVICE_HOST_SSL = "http://app.kjt.com/";
			break;
		}
	}

	protected static String read(String urlString)
			throws MalformedURLException, IOException, ServiceException
	{
		BetterHttpRequest request = BetterHttp.get(urlString);
		addAuthKeyHeader(request.unwrap());
		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	protected static String create(String urlString, String bodyString)
			throws MalformedURLException, IOException, ServiceException
	{
		return create(urlString, bodyString, false);
	}

	protected static String create(String urlString, String bodyString,
			boolean isForm) throws MalformedURLException, IOException,
			ServiceException
	{

		BetterHttpRequest request = buildRequest(urlString, bodyString, isForm);
		addAuthKeyHeader(request.unwrap());

		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	protected static String createRegister(String urlString, String bodyString)
			throws MalformedURLException, IOException, ServiceException
	{

		BetterHttpRequest request = buildRequest(urlString, bodyString, false);

		addAuthKeyHeader(request.unwrap());
		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			Header header = response.unwrap().getFirstHeader(
					RESPONSE_COOKIE_KEY);
			if (header != null)
			{
				CustomerUtil.cacheAuthenTick(formatCookie(header.getValue()));
			}
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	protected static String loginPost(String urlString, String bodyString)
			throws MalformedURLException, IOException, ServiceException
	{

		BetterHttpRequest request = buildRequest(urlString, bodyString, true);
		request.unwrap().addHeader(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			Header header = response.unwrap().getFirstHeader(
					RESPONSE_COOKIE_KEY);
			if (header != null)
			{
				CustomerUtil.cacheAuthenTick(formatCookie(header.getValue()));
			}
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	protected static String forgotPassword(String urlString, String bodyString)
			throws MalformedURLException, IOException, ServiceException
	{

		BetterHttpRequest request = buildRequest(urlString, bodyString, false);
		addForgotPasswordHeader(request.unwrap());
		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			Header header = response.unwrap().getFirstHeader(
					RESPONSE_COOKIE_KEY);
			if (header != null)
			{
				ForgotPasswordUtil.cacheCookie(formatCookie(header.getValue()));
			}
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	protected static String update(String urlString, String bodyString)
			throws MalformedURLException, IOException, ServiceException
	{
		return update(urlString, bodyString, false);
	}

	protected static String update(String urlString, String bodyString,
			boolean isForm) throws MalformedURLException, IOException,
			ServiceException
	{
		// TODO:
		// 虽然update和create都是post，但是他们有个标记:"connection.addRequestProperty("X-OP", "Update");"
		// 还是不一样，所以还需评估在这里是否可以直接调用create方法
		return create(urlString, bodyString, isForm);
	}

	protected static String delete(String urlString)
			throws MalformedURLException, IOException, ServiceException
	{
		BetterHttpRequest request = BetterHttp.delete(urlString);
		addAuthKeyHeader(request.unwrap());
		BetterHttpResponse response = request.send();

		int statusCode = response.getStatusCode();

		if (isSuccessful(statusCode))
		{
			return response.getResponseBodyAsString();
		}

		throw new ServiceException(statusCode);
	}

	private static boolean isSuccessful(int statusCode)
	{
		return statusCode >= 200 && statusCode < 300;
	}

	private static BetterHttpRequest buildRequest(String urlString,
			String bodyString, boolean isForm) throws MalformedURLException,
			IOException, ServiceException
	{
		HttpEntity entity = null;
		BetterHttpRequest request = null;

		if (isForm)
		{
			// TODO: 研究下看是否可以用UrlEncodedFormEntity
			StringEntity stringEntity = new StringEntity(bodyString);
			stringEntity.setContentType("application/x-www-form-urlencoded");
			entity = stringEntity;
			request = BetterHttp.post(urlString, entity);
			// request.unwrap().addHeader("Content-type","application/x-www-form-urlencoded");
		} else
		{
			entity = new JsonEntity(bodyString);
			request = BetterHttp.post(urlString, entity);
		}

		return request;
	}

	private static void addAuthKeyHeader(HttpUriRequest request)
	{
		String authKey = CustomerUtil.getAuthenTick();
		if (authKey != null && authKey.length() > 0)
		{
			request.addHeader(REQUEST_COOKIE_KEY, authKey);
		}

		request.addHeader(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
	}

	private static void addForgotPasswordHeader(HttpUriRequest request)
	{
		if (!StringUtil.isEmpty(ForgotPasswordUtil.getCookie()))
		{
			request.addHeader(REQUEST_COOKIE_KEY,
					ForgotPasswordUtil.getCookie());
		}

		request.addHeader(XNEWEGGAPPID_KEY, XNEWEGGAPPID_VALUE);
	}

	private static String formatCookie(String cookie)
	{
		if (!StringUtil.isEmpty(cookie))
		{
			return cookie.substring(0, cookie.indexOf(";"));
		}

		return cookie;

	}
}

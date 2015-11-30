/***************************************************************** 
 * Copyright (C) Newegg Corporation. All rights reserved.
 * 
 * Author: Aki Xie (Aki.C.Xie@newegg.com)
 * Create Date: 09/24/2010
 * Usage:
 *
 * RevisionHistory
 * Date         Author               Description
 *
 *****************************************************************/

package com.ifootball.app.framework.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.text.TextUtils;

import com.ifootball.app.util.PersistenceKey;

public class HttpClient {
	public static final int CREATE_MODE = 0;
	public static final int READ_MODE = 1;
	public static final int UPDATE_MODE = 2;
	public static final int DELETE_MODE = 3;
	private static final int CONNECTION_TIMEOUT = 12000;
	private static final int READ_TIMEOUT = 60000;
	private static final int STREAM_BUFFER_SIZE = 4 * 1024;

	static {
		trustAllHosts();
	}

	public HttpClient() {
	}

	private int mode;
	private URL url;
	private Map<String, String> headerValues;
	private HttpURLConnection connection;
	private InputStream bodyStream;

	public HttpClient createMode() {
		return mode(CREATE_MODE);
	}

	public HttpClient readMode() {
		return mode(READ_MODE);
	}

	public HttpClient updateMode() {
		return mode(UPDATE_MODE);
	}

	public HttpClient deleteMode() {
		return mode(DELETE_MODE);
	}

	/**
	 * Sets the HTTP verb.
	 * 
	 * @param mode
	 *            Must be one of the known modes otherwise it will be casted to
	 *            be one of the known modes.
	 * @return Itself.
	 */
	public HttpClient mode(int mode) {
		this.mode = mode % 4;
		return this;
	}

	public HttpClient url(String urlString) throws MalformedURLException {
		url = new URL(urlString);
		return this;
	}

	public HttpClient url(URL url) {
		this.url = url;
		return this;
	}

	public HttpClient header(String header, String value) {
		ensureHeaderValuesNotNull();
		headerValues.put(header, value);
		return this;
	}

	public HttpClient body(InputStream stream) {
		bodyStream = stream;
		return this;
	}

	/**
	 * Every request related setting is set and performed here, everything else
	 * is cleared.
	 * 
	 * @throws IOException
	 */
	public void send(boolean isWithCache) throws IOException {
		if (connection != null) {
			connection.disconnect();
			connection = null;
		}
		// check is ssl connection or not, add by tracy 2010-12-14
		if (url.getProtocol().toLowerCase().equals("https")) {
			HttpsURLConnection https = (HttpsURLConnection) url
					.openConnection();
			https.setHostnameVerifier(DO_NOT_VERIFY);
			connection = https;
		} else {
			connection = (HttpURLConnection) url.openConnection();
		}
		HttpURLConnection connection = this.connection;
		if (connection == null) {
			return;
		}
		switch (mode) {
		case CREATE_MODE:
			connection.setRequestMethod("POST");
			break;
		case READ_MODE:
			connection.setRequestMethod("GET");
			break;
		case UPDATE_MODE:
			connection.setRequestMethod("POST");
			connection.addRequestProperty("X-OP", "Update");
			break;
		case DELETE_MODE:
			connection.setRequestMethod("DELETE");
		default:
			break;
		}
		if (headerValues != null) {
			Map<String, String> headerValues = this.headerValues;
			for (String header : headerValues.keySet()) {
				connection.setRequestProperty(header, headerValues.get(header));
			}
			if(PersistenceKey.AUTHENTICATION_VALUE!=null){
				connection.setRequestProperty(PersistenceKey.AUTHENTICATION_KEY,PersistenceKey.AUTHENTICATION_VALUE);
			}
		}
		connection.setConnectTimeout(CONNECTION_TIMEOUT);
		connection.setReadTimeout(READ_TIMEOUT);
		connection.setInstanceFollowRedirects(true);
		if (bodyStream != null) {
			connection.setUseCaches(false);
			connection.setDoOutput(true);
			copyStream(bodyStream, connection.getOutputStream());
		} else {
			connection.setUseCaches(isWithCache);
		}
		try {
			connection.connect();
		} catch (NullPointerException e) {
//			connection.connect();
		}
	}

	private void copyStream(InputStream from, OutputStream to) {
		byte[] buffer = new byte[STREAM_BUFFER_SIZE];
		int read;
		try {
			while ((read = from.read(buffer)) > -1) {
				to.write(buffer, 0, read);
			}
		} catch (IOException ex) {

		}
	}

	public boolean isSuccessful() {
		if (connection != null) {
			try {
				int code = connection.getResponseCode();
				return (code >= 200 && code < 300)
						|| (connection.getUseCaches() && code == -1);
			} catch (Exception ex) {
                return false;
			}
		}
		return false;
	}

	public String getBodyAsString() {
		if (connection != null) {
			HttpURLConnection connection = this.connection;
			InputStreamReader reader = null;
			try {
				String contentEncoding = connection.getContentEncoding();
				String contentType = getContentType(connection.getContentType());
				Charset charset = Charset.forName(contentType);
				// 2010-11-24 colin.z.chen add gzip.deflate judge.
				if ("gzip".equals(contentEncoding)) {
					reader = new InputStreamReader(new GZIPInputStream(
							connection.getInputStream()), charset);
				} else if ("deflate".equals(contentEncoding)) {
					reader = new InputStreamReader(new InflaterInputStream(
							connection.getInputStream()), charset);
				} else {
					reader = new InputStreamReader(connection.getInputStream(),
							charset);
				}
				StringBuilder builder = new StringBuilder();
				char[] buffer = new char[STREAM_BUFFER_SIZE];
				int read;
				while ((read = reader.read(buffer)) > -1) {
					builder.append(buffer, 0, read);
				}
				return builder.toString();
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException ex) {

					}
				}
			}
		}
		return null;
	}

	public boolean isConnected() {
		return connection != null;
	}

	public String getContentType() {
		if (connection != null) {
			return connection.getContentType();
		}
		return null;
	}

	public int getContentLength() {
		if (connection != null) {
			return connection.getContentLength();
		}
		return 0;
	}

	public String getContentEncoding() {
		if (connection != null) {
			return connection.getContentEncoding();
		}
		return null;
	}

	public int getResponseCode() throws IOException {
		if (connection != null) {
			try {
				return connection.getResponseCode();
			} catch (Exception e) {
				return -1;
			}
		}
		return -1;
	}

	public String getHeader(String header) {
		if (connection != null) {
			return connection.getHeaderField(header);
		}
		return null;
	}

	public InputStream getBody() {
		if (connection != null) {
			try {
				return connection.getInputStream();
			} catch (IOException ex) {
				// return null in case of error.
			}
		}
		return null;
	}

	// 2010-11-24 colin.z.chen add.
	private String getContentType(String contentTypeString) {
		String contentType = contentTypeString;
		String charset = "UTF-8";
		if (contentType != null) {
			String[] values = contentType.split(";");
			for (String value : values) {
				value = value.trim();
				if (value.toLowerCase().startsWith("charset=")) {
					charset = value.substring("charset=".length());
				}
			}
			if (TextUtils.isEmpty(charset)) {
				charset = "UTF-8";
			}
		}
		return charset;
	}

	private void ensureHeaderValuesNotNull() {
		if (headerValues == null) {
			headerValues = new HashMap<String, String>();
		}
	}

	public void sendWith302CookieSet() throws IOException {
		send(true); // send original request
		if (connection != null) {
			connection.setInstanceFollowRedirects(false);
			int code = connection.getResponseCode();
			if (code == 302) {
				List<String> cookies = connection.getHeaderFields().get(
						"set-cookie");
				StringBuilder cookieBuilder = new StringBuilder();
				for (int i = 0; i < cookies.size(); i++) {
					if (cookies.get(i) != null) {
						cookieBuilder.append(String.format("%s; ", cookies
								.get(i)));
					}
				}
				String cookie = cookieBuilder.toString().trim();
				cookie = cookie.substring(0, cookie.length() - 1); // remove
				// last
				// semicolon
				String redirectLocation = connection.getHeaderField("Location");
				this.url = new URL(String.format("%s://%s%s",
						url.getProtocol(), url.getHost(), redirectLocation));
				headerValues.put("Cookie", cookie);
				send(true); // send redirect request with cookie
			}
		}
	}

	/*
	 * always verify the host - dont check for certificate add by tracy
	 * 2010-12-14
	 */
	final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	/**
	 * Trust every server - dont check for any certificate add by tracy
	 * 2010-12-14
	 */
	private static void trustAllHosts() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}
		} };

		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

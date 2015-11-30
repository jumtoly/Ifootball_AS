package com.ifootball.app.framework.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.Header;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

public class JsonEntity extends StringEntity {

	public JsonEntity(String s) throws UnsupportedEncodingException {
		this(s, HTTP.UTF_8);
	}
	
	public JsonEntity(String s, String charset) throws UnsupportedEncodingException {
		super(s, charset);
	}

	@Override
	public Header getContentType() {
        return new BasicHeader(HTTP.CONTENT_TYPE, "application/json");
	}

}
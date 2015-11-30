package com.ifootball.app.webservice;

import java.io.IOException;
import java.lang.reflect.Type;

import android.net.Uri;

import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.VersionInfo;
import com.ifootball.app.util.VersionUtil;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;


public class VersionService extends BaseService {
	public ResultData<VersionInfo> checkVersionUpdate() throws IOException, JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/Version/CheckVersionUpdate");
		b.appendQueryParameter("versionCode", VersionUtil.getCurrentVersion());
		String url = b.build().toString();

		String jsonString = read(url);
		Type type = new TypeToken<ResultData<VersionInfo>>() {}.getType();
		
		return new Gson().fromJson(jsonString, type);
	}
}

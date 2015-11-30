package com.ifootball.app.webservice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import android.net.Uri;

import com.ifootball.app.entity.AreaInfo;
import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.ResultData;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

public class CommonService extends BaseService {
	public List<AreaInfo> GetDistrictByCity(int citysysno) throws IOException,
			JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/common/GetDistrictByCity");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = read(url + "?id=" + citysysno);
		Type listType = new TypeToken<ResultData<List<AreaInfo>>>() {
		}.getType();
		ResultData<List<AreaInfo>> resultData = gson.fromJson(jsonString,
				listType);
		return resultData.getData();
	}

	public ResultData<String> sendValidateCode(String cellphone)
			throws IOException, JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/common/AjaxSendValidateCellphone");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = read(url + "?id=" + cellphone);
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<String> resultData = gson.fromJson(jsonString, listType);
		return resultData;
	}

}

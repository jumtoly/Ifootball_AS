package com.ifootball.app.webservice;

import java.io.IOException;
import java.lang.reflect.Type;

import android.net.Uri;

import com.ifootball.app.entity.CustomerInfo;
import com.ifootball.app.entity.RegisterInfo;
import com.ifootball.app.entity.ResultData;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

public class CustomerService extends BaseService {
	public ResultData<CustomerInfo> register(RegisterInfo info)
			throws IOException, JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/loginreg/register");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = createRegister(url, gson.toJson(info));
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<CustomerInfo> resultData = gson.fromJson(jsonString,
				listType);
		return resultData;
	}

	public ResultData<String> sendValidateCode(String cellphone)
			throws IOException, JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/loginreg/AjaxSendValidateCellphoneByCodeTemp");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = create(url, "{\"Cellphone\":" + cellphone + "}");
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<String> resultData = gson.fromJson(jsonString, listType);
		return resultData;
	}

	public ResultData<CustomerInfo> login(String CellPhone, String Password,
			int RememberLogin) throws IOException, JsonParseException,
			ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/loginreg/login");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = loginPost(url, "Cellphone=" + CellPhone
				+ "&Password=" + Password + "&RememberLogin=" + RememberLogin);
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<CustomerInfo> resultData = gson.fromJson(jsonString,
				listType);
		return resultData;
	}

	public ResultData<String> findPassword(String CellPhone, String Password,
			String OldPassword, String ConfirmKey) throws IOException,
			JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/Common/ResetPassword");
		String url = b.build().toString();
		Gson gson = new Gson();
		String jsonString = create(url, "{\"Cellphone\":\"" + CellPhone
				+ "\",\"Password\":\"" + Password + "\",\"ConfirmKey\":"
				+ ConfirmKey + ",\"RePassword\":\"" + Password
				+ "\",\"OldPassword\":\"" + OldPassword + "\"}");
		Type listType = new TypeToken<ResultData<String>>() {
		}.getType();
		ResultData<String> resultData = gson.fromJson(jsonString, listType);
		return resultData;
	}

	public ResultData<CustomerInfo> load() throws IOException,
			JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/customer/load");
		Gson gson = new Gson();
		String jsonString = read(b.build().toString());
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<CustomerInfo> resultData = gson.fromJson(jsonString,
				listType);
		return resultData;
	}

	public ResultData<String> update(CustomerInfo info)
			throws IOException, JsonParseException, ServiceException {
		Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
		b.path("/customer/update");
		Gson gson = new Gson();
		String jsonString = update(b.build().toString(), gson.toJson(info));
		Type listType = new TypeToken<ResultData<CustomerInfo>>() {
		}.getType();
		ResultData<String> resultData = gson.fromJson(jsonString,
				listType);
		return resultData;
	}
}

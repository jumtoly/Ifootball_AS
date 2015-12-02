package com.ifootball.app.webservice.green;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.ifootball.app.entity.AreaInfo;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.VenueDetailInfo;
import com.ifootball.app.entity.VenueSearchCriteria;
import com.ifootball.app.entity.VenueSearchResultInfo;
import com.ifootball.app.webservice.BaseService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.JsonParseException;
import com.neweggcn.lib.json.reflect.TypeToken;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

public class GreenService extends BaseService {
    public VenueSearchResultInfo search(VenueSearchCriteria criteria)
            throws IOException, JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/venue/query");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = create(url, gson.toJson(criteria));
        Type listType = new TypeToken<ResultData<VenueSearchResultInfo>>() {
        }.getType();
        ResultData<VenueSearchResultInfo> resultData = gson.fromJson(
                jsonString, listType);
        return resultData.getData();
    }

    public VenueDetailInfo LoadVenue(int id) throws IOException,
            JsonParseException, ServiceException {
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/venue/LoadVenue");
        String url = b.build().toString();
        Gson gson = new Gson();
        String jsonString = read(url + "?id=" + id);
        Type listType = new TypeToken<ResultData<VenueDetailInfo>>() {
        }.getType();
        ResultData<VenueDetailInfo> resultData = gson.fromJson(jsonString,
                listType);
        return resultData.getData();
    }

}

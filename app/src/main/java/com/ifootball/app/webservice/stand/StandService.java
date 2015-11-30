package com.ifootball.app.webservice.stand;

import android.net.Uri;

import com.ifootball.app.common.Common;
import com.ifootball.app.common.StandPageTypeEnum;
import com.ifootball.app.entity.CurrentLocation;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.stand.DynamicInfo;
import com.ifootball.app.entity.stand.StandInfo;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.webservice.BaseService;
import com.ifootball.app.webservice.ServiceException;
import com.neweggcn.lib.json.Gson;
import com.neweggcn.lib.json.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by liu.yao on 2015/11/27.
 */
public class StandService extends BaseService {

    private static float latitude;// 纬度
    private static float longitude;// 经度

    static {
        latitude = Float.parseFloat(MySharedCache.get(Common.CURRENT_LATITUDE.name(), "0"));
        longitude = Float.parseFloat(MySharedCache.get(Common.CURRENT_LONGITUDE.name(), "0"));
    }

    public DynamicInfo getDynamicInfo(int pageIndex, int pageSize, int standType) throws IOException, ServiceException {

        CurrentLocation location = new CurrentLocation((float) 30.549754, (float) 104.045816, standType, pageIndex, pageSize);
        Uri.Builder b = Uri.parse(RESTFUL_SERVICE_HOST_WWW).buildUpon();
        b.path("/topic/query");
        String url = b.build().toString();

        Gson gson = new Gson();
        String jsonString = create(url, gson.toJson(location));


        Type type = new TypeToken<ResultData<DynamicInfo>>() {
        }.getType();
        ResultData<DynamicInfo> resultData = gson.fromJson(jsonString, type);

        return resultData.getData();
    }
}

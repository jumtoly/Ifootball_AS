package com.ifootball.app.entity.stand;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.ifootball.app.entity.HasCollection;
import com.ifootball.app.entity.HasPageInfo;
import com.ifootball.app.entity.PageInfo;
import com.neweggcn.lib.json.annotations.SerializedName;

public class DynamicInfo implements HasPageInfo, HasCollection<StandInfo>, Serializable {
    private static final long serialVersionUID = -225227035757722040L;

    @SerializedName("ResultList")
    private List<StandInfo> standInfos;

    @SerializedName("PageInfo")
    private PageInfo pageInfo;

    
    @Override
    public Collection<StandInfo> getList() {
        return standInfos;
    }

    @Override
    public PageInfo getPageInfo() {
        return pageInfo;
    }
}

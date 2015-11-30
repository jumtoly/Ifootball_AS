package com.ifootball.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.util.WXUtil;
import com.ifootball.app.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	public static final String WX_PAY_RESULT_STATUS_DATA_KEY = "WX_PAY_RESULT_STATUS_DATA";

	private IWXAPI mIWXAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wxentry_layout);
		mIWXAPI = WXUtil.createWXAPI(WXPayEntryActivity.this);
		mIWXAPI.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		mIWXAPI.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq arg0) {

	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			MySharedCache.put(WX_PAY_RESULT_STATUS_DATA_KEY, false);
			switch (resp.errCode) {
			case 0:
				MySharedCache.put(WX_PAY_RESULT_STATUS_DATA_KEY, true);
				MyToast.show(WXPayEntryActivity.this, "支付成功");
				break;
			case -1:
				MyToast.show(WXPayEntryActivity.this, "支付失败");
				break;
			case -2:
				MyToast.show(WXPayEntryActivity.this, "用户取消支付");
				break;
			}
		}
		finish();
	}
}

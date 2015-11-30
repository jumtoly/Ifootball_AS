package com.ifootball.app.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.util.WXUtil;
import com.ifootball.app.R;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * 微信回调
 * @author kjt-cd035
 *
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI mIWXAPI;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wxentry_layout);
		mIWXAPI = WXUtil.createWXAPI(WXEntryActivity.this);
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
			switch (resp.errCode) {
			case 0:
				MyToast.show(WXEntryActivity.this, "支付成功");
				break;
			case -1:
				MyToast.show(WXEntryActivity.this, "支付失败");
				break;
			case -2:
				MyToast.show(WXEntryActivity.this, "用户取消支付");
				break;
			}
		} else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {//用户登录
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				String code = ((SendAuth.Resp) resp).code;//通过code获取access_token
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权

				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
				break;
			default:
				break;
			}
		} else {
			int result = 0;
			switch (resp.errCode) {
			case BaseResp.ErrCode.ERR_OK:
				result = R.string.errcode_success;
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				result = R.string.errcode_cancel;
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				result = R.string.errcode_deny;
				break;
			default:
				result = R.string.errcode_unknown;
				break;
			}
			MyToast.show(WXEntryActivity.this, getResources().getString(result));
		}
		finish();
	}

}

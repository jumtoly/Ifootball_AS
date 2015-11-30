package com.ifootball.app.util;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.ifootball.app.framework.widget.MyToast;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * 微信支付
 * @author kjt-cd035
 *
 */
public class WXPayUtil {
	private static final String PARTNER_ID_KEY="1228159402";//商户号
	private static final String APP_SECRET_KEY="8934e7d15a53e97507ef7941f7b0519d";//商户平台设置的密钥
	
	private IWXAPI mIWXAPI;
	private Context mContext;
	
	public WXPayUtil(Context context){
		if(mIWXAPI==null){
			mContext=context;
			mIWXAPI=WXUtil.initWXAPI(context);
		}
	}
	
	/**
	 * 支付
	 * @param prepayId
	 * 	预支付交易会话ID(微信返回的支付交易会话ID)
	 */
	public void pay(String prepayId){
		if (mIWXAPI.getWXAppSupportAPI()==0) {
			MyToast.show(mContext, "请安装微信APP");
			return;
		}
		mIWXAPI.sendReq(getPayReq(prepayId));
	}
	
	/**
	 * 获取PayReq
	 * @param prepayId
	 * 		预支付交易会话ID(微信返回的支付交易会话ID)
	 * @return
	 */
	private PayReq getPayReq(String prepayId){
		PayReq request = new PayReq();
		request.appId = WXUtil.APP_WEI_XIN_ID;
		request.partnerId = PARTNER_ID_KEY;//商户号
		request.prepayId= prepayId;//预支付交易会话ID(微信返回的支付交易会话ID)
		request.packageValue = "Sign=WXPay";//扩展字段(暂填写固定值Sign=WXPay)
		request.nonceStr= WXUtil.getNonceStr();//随机字符串(随机字符串，不长于32位)
		request.timeStamp= WXUtil.getTimeStamp();//时间戳
		request.sign= sign(request);//签名
		
		return request;
	}
	
	private String sign(PayReq req){
		List<NameValuePair> signParams = new LinkedList<NameValuePair>();
		signParams.add(new BasicNameValuePair("appid", req.appId));
		signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
		signParams.add(new BasicNameValuePair("package", req.packageValue));
		signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
		signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
		signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
		
		return createSign(signParams);
	}
	
	/**
	 * 生成签名信息
	 * @param params
	 * @return
	 */
	private String createSign(List<NameValuePair> params) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < params.size(); i++) {
			sb.append(params.get(i).getName());
			sb.append('=');
			sb.append(params.get(i).getValue());
			sb.append('&');
		}
		sb.append("key=");
		sb.append(APP_SECRET_KEY);

		return MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
	}
}

package com.ifootball.app.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.R;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;

/**
 * 微信分享
 * @author kjt-cd035
 *
 */
public class WXShareUtil {
	private static int WXUTIL_FRIEND_KEY = 1;
	private static int WXUTIL_FRIEND_CIRCLE_KEY = 2;

	private IWXAPI mIWXAPI;
	private PopupWindow mPopupWindow;
	private View mView;
	private Context mContext;
	private String mUrl;
	private String mContent;
	private String mDescription;
	private Bitmap mThumb;
	private Bitmap mBitmap;

	public WXShareUtil(Context context) {
		mContext = context;
		if (mIWXAPI == null) {
			mIWXAPI = WXUtil.initWXAPI(context);
		}
		initPopupWindowView(context);
	}

	public void shareText(String content, String description) {
		share(null, content, description, null, null);
	}

	public void shareLink(String url, String content, String description,
			Bitmap thumb) {
		share(url, content, description, thumb, null);
	}

	public void shareImage(String content, String description, Bitmap thumb,
			Bitmap bitmap) {
		share(null, content, description, thumb, bitmap);
	}

	private void share(String url, String content, String description,
			Bitmap thumb, Bitmap bitmap) {
		initData(url, content, description, thumb, bitmap);
		if (mPopupWindow != null) {
			if (mPopupWindow.isShowing()) {
				mPopupWindow.dismiss();
			} else {
				try {
					mPopupWindow.showAtLocation(((Activity) mContext)
							.getWindow().getDecorView(), Gravity.TOP, 0, 0);
				} catch (Exception e) {
				}
			}
		}
	}

	private void initData(String url, String content, String description,
			Bitmap thumb, Bitmap bitmap) {
		mUrl = url;
		mContent = content;
		mDescription = description;
		mThumb = thumb;
		mBitmap = bitmap;
	}

	private void setShare(int type, String url, String content,
			String description, Bitmap thumb, Bitmap bitmap) {
		if (mIWXAPI != null) {
			if (mIWXAPI.getWXAppSupportAPI() == 0) {
				MyToast.show(mContext, "请安装微信APP");
				return;
			}
			if (type == WXUTIL_FRIEND_KEY) {
				share(SendMessageToWX.Req.WXSceneSession, url, content,
						description, thumb, bitmap);
			} else if (type == WXUTIL_FRIEND_CIRCLE_KEY) {
				if (mIWXAPI.getWXAppSupportAPI() >= 0x21020001) {
					share(SendMessageToWX.Req.WXSceneTimeline, url, content,
							description, thumb, bitmap);
				} else {
					MyToast.show(mContext, "微信4.2以上支持分享到朋友圈");
				}
			}
		}
	}

	private void share(int scene, String url, String content,
			String description, Bitmap thumb, Bitmap bitmap) {
		WXMediaMessage msgMediaMessage = null;
		if (url != null && !"".equals(url.trim())) {
			WXWebpageObject webpage = new WXWebpageObject();
			webpage.webpageUrl = url;

			msgMediaMessage = new WXMediaMessage(webpage);
			msgMediaMessage.title = content;
			msgMediaMessage.description = description;
			if (thumb != null) {
				msgMediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumb,
						true);
			}
		} else if (bitmap != null) {
			WXImageObject imageObject = new WXImageObject(bitmap);

			msgMediaMessage = new WXMediaMessage();
			msgMediaMessage.mediaObject = imageObject;
			msgMediaMessage.title = content;
			msgMediaMessage.description = description;
			if (thumb != null) {
				msgMediaMessage.thumbData = BitmapUtil.bmpToByteArray(thumb,
						true);
			}
		} else {
			WXTextObject textObj = new WXTextObject();
			textObj.text = content;

			msgMediaMessage = new WXMediaMessage();
			msgMediaMessage.mediaObject = textObj;
			msgMediaMessage.description = description;
		}

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = String.valueOf(System.currentTimeMillis());
		req.message = msgMediaMessage;
		req.scene = scene;

		mIWXAPI.sendReq(req);
	}

	private void initPopupWindowView(Context context) {
		mView = LayoutInflater.from(context).inflate(R.layout.wx_select_layout,
				null);
		initPopupWindow(true);
		mView.findViewById(R.id.wx_friend_textview).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setShare(WXUTIL_FRIEND_KEY, mUrl, mContent,
								mDescription, mThumb, mBitmap);
						close();
					}
				});
		mView.findViewById(R.id.wx_friend_circle_textview).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						setShare(WXUTIL_FRIEND_CIRCLE_KEY, mUrl, mContent,
								mDescription, mThumb, mBitmap);
						close();
					}
				});
	}

	private void initPopupWindow(boolean isBack) {
		mPopupWindow = new PopupWindow(mView,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, true);
		if (isBack) {
			mPopupWindow.setOutsideTouchable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		}
	}

	private void close() {
		if (mPopupWindow != null) {
			mPopupWindow.dismiss();
		}
	}
}

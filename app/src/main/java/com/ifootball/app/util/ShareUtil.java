package com.ifootball.app.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ifootball.app.R;

/*
 * 分享工具类
 */
public class ShareUtil {
	public static final int IMAGE_LOAD_RESULT_MESSAGE_KEY = 190;

	private static String product_url = "http://m.kjt.com/product/{0}";
	private static String groupBuy_url = "nhttp://m.kjt.com/product/{0}";

	/**
	 * 获取商品分享链接地址
	 * @param productSysNo
	 * @return
	 */
	public static String getShareProductUrl(int productSysNo) {
		return StringUtil.format(product_url, productSysNo);
	}

	/**
	 * 获取团购分享链接地址
	 * @param groupBuyingSysNo
	 * @return
	 */
	public static String getShareGroupBuyUrl(int groupBuyingSysNo) {
		return StringUtil.format(groupBuy_url, groupBuyingSysNo);
	}

	/**
	 * 格式化分享内容
	 * @param title
	 * @param price
	 * @return
	 */
	public static String frmatTitle(String title, double price) {
		if (price > 0) {
			title = "跨境通--" + StringUtil.priceToString(price) + "--" + title;
		} else {
			title = "跨境通--" + title;
		}

		return title;
	}

	/**
	 * 获取分享icon
	 * @param context
	 * @return
	 */
	public static Bitmap getShareBitmap(Context context) {
		Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),
				R.mipmap.app_icon);
		return thumb;
	}

	/*
	 * 分享商品
	 */
	//	public static void shareProduct(Activity activity,String productName,int productSysNo) {
	//		String shareContent = StringUtil.format(product_tmpl, productName,productSysNo);
	//		startShare(activity,shareContent);
	//	}

	/*
	 * 分享团购活动
	 */
	//	public static void shareGroupBuy(Activity activity,String productName,int sysNo) {
	//		String shareContent = StringUtil.format(groupBuy_tmpl, productName,sysNo);
	//		startShare(activity,shareContent);
	//	}

	//	private static void startShare(Activity activity,String content) {
	//		Intent intent=new Intent(Intent.ACTION_SEND);  
	//		  
	//		intent.setType("text/plain");  
	//		intent.putExtra(Intent.EXTRA_SUBJECT, "分享");  
	//		intent.putExtra(Intent.EXTRA_TEXT, content);
	//		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	//		activity.startActivity(Intent.createChooser(intent, "请选择分享方式"));  
	//	}

	//	private static String product_tmpl = "#跨境通#的{0} 很划算哦！\nhttp://www.kjt.com/product/detail/{1}";
	//	private static String groupBuy_tmpl = "#跨境通#的{0} 很划算哦！\nhttp://www.kjt.com/groupbuying/{1}";
}

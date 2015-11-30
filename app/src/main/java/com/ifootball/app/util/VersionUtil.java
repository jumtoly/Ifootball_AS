package com.ifootball.app.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.ifootball.app.base.BaseApp;
import com.ifootball.app.entity.BizException;
import com.ifootball.app.entity.ResultData;
import com.ifootball.app.entity.VersionInfo;
import com.ifootball.app.framework.cache.MySharedCache;
import com.ifootball.app.util.MyAsyncTask.OnError;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.webservice.VersionService;
import com.neweggcn.lib.json.JsonParseException;

public class VersionUtil {
	
	private static final String VERSION_UTIL_IGNORE_CODE_KEY="VERSION_UTIL_IGNORE_CODE"; 
	private static final int DOWNLOAD_MAX_PROGRESS_LENGTH_KEY=10;
	private static final int DOWNLOAD_PROGRESS_LENGTH_KEY=11;
	private static final String PKG_NAME="kjt-android-phone-release.apk";

	private Handler mHandler;
	public ProgressDialog mProgressDialog;
	private boolean mIsDownload=true;
	private String mCacheRoot;
	private boolean mIsUpdate=false;
	private VersionInfo mVersionInfo;
	private static VersionUtil mVersionUtil;
	private Context mContext;
	private static boolean mRedirectIgnore=false;//跳过忽略
	
	public VersionUtil(){
		setCacheRoot();
		setHandler();
	}
	
	public static VersionUtil getInstance() {
		if (mVersionUtil==null) {
			mVersionUtil=new VersionUtil();
		}
		
		return mVersionUtil;
	}
	
	public boolean IsUpdate(){
		return mIsUpdate;
	}
	
	/**
	 * 获取当前版本号
	 * @return
	 */
	public static String getCurrentVersion() {
		String clientVersion = null;
		try {
			clientVersion = BaseApp.instance().getPackageManager().getPackageInfo(
					BaseApp.instance().getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return clientVersion;
	}
	
	/**
	 * 获取是否忽略当前版本
	 */
	public static boolean isIgnore(String serveVersionCode){
		String cacheVersionCode=MySharedCache.get(VERSION_UTIL_IGNORE_CODE_KEY, "0");
		if (formatVersionCode(cacheVersionCode)==formatVersionCode(serveVersionCode)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 设置忽略
	 */
	public static void setIgnore(String serveVersionCode) {
		MySharedCache.put(VERSION_UTIL_IGNORE_CODE_KEY, serveVersionCode);
	}
	
	/**
	 * 跳过忽略
	 */
	public static void redirectIgnore() {
		mRedirectIgnore=true;
	}
	
	/**
	 * 检查更新
	 */
	public void checkVersionUpdate() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				checkVersionUpdate(null);
			}
		}, 1);
	}
	
	public void checkVersionUpdate(final OnCheckUpdate onCheckUpdate){
		if(onCheckUpdate != null){
			onCheckUpdate.beforeCallService();
		}
		MyAsyncTask<ResultData<VersionInfo>> task=new MyAsyncTask<ResultData<VersionInfo>>(BaseApp.instance()){

			@Override
			public ResultData<VersionInfo> callService() throws IOException,
					JsonParseException, BizException, ServiceException {
				return new VersionService().checkVersionUpdate();
			}

			@Override
			public void onLoaded(ResultData<VersionInfo> result) throws Exception {
				if (result!=null&&result.isSuccess()&&result.getData()!=null) {
					mVersionInfo=result.getData();
					if ((result.getData().getIsForcedUpdate())||(result.getData().getIsUpdate()&&!isIgnore(result.getData().getCode()))) {
						mIsUpdate=true;
					}
					if(onCheckUpdate != null){
						onCheckUpdate.afterCallService(mVersionInfo);
					}
				}
			}
		};
		task.setOnError(new OnError() {

			@Override
			public void handleError(Exception e) {
			}
		});
		task.executeTask();
	}
	
	private void download(final String url) {
		if (!StringUtil.isEmpty(url)) {
			mProgressDialog.show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					HttpClient client = new DefaultHttpClient();
					HttpGet httpGet = new HttpGet(url);
					HttpResponse response;
					try {
						response = client.execute(httpGet);
						HttpEntity entity = response.getEntity();
						long length = entity.getContentLength();
						sendMessage(DOWNLOAD_MAX_PROGRESS_LENGTH_KEY,length);//发送资源大小
						InputStream inputStream = entity.getContent();
						FileOutputStream fileOutputStream = null;
						if (inputStream!=null) {
							delFile();//删除原来的app
							File apkFile = new File(mCacheRoot, PKG_NAME);
							fileOutputStream = new FileOutputStream(apkFile);
							byte[] buf = new byte[1024];
							int ch = -1;
							int count = 0;
							mIsDownload = true;
							while ((ch = inputStream.read(buf)) != -1) {
								if (mIsDownload) {
									fileOutputStream.write(buf, 0, ch);
									count += ch;
									sendMessage(DOWNLOAD_PROGRESS_LENGTH_KEY,count);//发送下载大小
								}
							}
						}
						fileOutputStream.flush();
						if (fileOutputStream != null) {
							fileOutputStream.close();
						}
						if (mIsDownload) {
							down();
						}
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
	
	private void down() {
		mHandler.post(new Runnable() {
			public void run() {
				mProgressDialog.cancel();
				install();
			}
		});
	}
	
	private void install() {
		File file = new File(mCacheRoot, PKG_NAME);
		if (!file.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//		BaseApp.instance().startActivity(intent);
		((Activity)mContext).startActivity(intent);
	}
	
	public void update(final Context context) {
		mContext=context;
		if(mVersionInfo == null){
			((Activity)context).finish();
			return;
		}
		if (mVersionInfo.getIsForcedUpdate()||(mVersionInfo.getIsUpdate()&&!isIgnore(mVersionInfo.getCode()))||(mRedirectIgnore)) {
			mRedirectIgnore=false;
			String title="发现新版本，请更新";
			String message=StringUtil.isEmpty(mVersionInfo.getDescription())?"修复bug":mVersionInfo.getDescription();
			String negativeTitle=mVersionInfo.getIsForcedUpdate()?"取消":"不再提示本次更新";
			if (mVersionInfo.getIsForcedUpdate()) {
				DialogUtil.getConfirmDialog(context, title, message, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mIsUpdate=true;
						setProgressDialog(mVersionInfo,context);
						download(mVersionInfo.getDownloadPath());
					}
				}).show();
			}else {
				DialogUtil.getAlertDialog(context, title, message,"确定", new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						setProgressDialog(mVersionInfo,context);
						download(mVersionInfo.getDownloadPath());
					}
					
				}, negativeTitle,new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface dialog, int which) {
						mIsUpdate=false;
						setIgnore(mVersionInfo.getCode());
						((Activity)context).finish();
					}
				}).show();
			}
		}else {
		   ((Activity)context).finish();
			return;
		}
	}
	
	private void setHandler() {
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case DOWNLOAD_MAX_PROGRESS_LENGTH_KEY:
					mProgressDialog.setMax((Integer.parseInt(msg.obj.toString()))/1000);
					break;
				case DOWNLOAD_PROGRESS_LENGTH_KEY:
					mProgressDialog.setProgress((Integer.parseInt(msg.obj.toString()))/1000);
					break;
				default:
					break;
				}
			}
		};
	}
	
	private void setProgressDialog(final VersionInfo info,final Context context) {
		mProgressDialog = new ProgressDialog(context);
		mProgressDialog.setMessage("正在下载,请稍候...");
		mProgressDialog.setCancelable(false);
		mProgressDialog.setButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				mIsDownload=false;
				mProgressDialog.cancel();
				if (info!=null&&info.getIsForcedUpdate()) {
					mIsUpdate=true;
				}
				((Activity)context).finish();
			}
		});
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	}
	
	private void sendMessage(int what,Object object) {
		Message msg=new Message();
        msg.what=what;
        msg.obj=object;
        mHandler.sendMessage(msg);
	}
	
	private void setCacheRoot(){
		String directory="download/";
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			mCacheRoot= Environment.getExternalStorageDirectory().getPath()+"/"+directory;
		}else {
			mCacheRoot= BaseApp.instance().getCacheDir().getPath()+"/"+directory;
		}
		createDirectory();
	}
	
	/**
	 * 创建目录
	 */
	private void createDirectory() {
		File file = new File(mCacheRoot);
		// 判断文件目录是否存在
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	/**
	 * 删除app
	 */
	private void delFile() {
		File apkFile = new File(mCacheRoot, PKG_NAME);
		if (apkFile.exists()) {
			apkFile.delete();
		}
	}

	private static int formatVersionCode(String versionCode) {
		if (!StringUtil.isEmpty(versionCode)) {
			versionCode=versionCode.replace(".", "");
			return Integer.parseInt(versionCode);
		}
		return 0;
	}
	
	public interface OnCheckUpdate{
		void beforeCallService();
		void afterCallService(VersionInfo versionInfo);
	}
}

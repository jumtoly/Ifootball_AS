package com.ifootball.app.util;

import java.io.IOException;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.ifootball.app.entity.BizException;
import com.ifootball.app.framework.widget.MyToast;
import com.ifootball.app.webservice.ServiceException;
import com.ifootball.app.R;
import com.neweggcn.lib.json.JsonParseException;

public abstract class MyAsyncTask<T> extends AsyncTask<Void, Void, T> {

	public static interface OnError {

		public void handleError(Exception e);
	}

	public abstract T callService() throws IOException, JsonParseException,
			BizException, ServiceException;

	public abstract void onLoaded(T result) throws Exception;

	private Context mContext;
	private OnError mOnError;

	public void setOnError(OnError mOnError) {

		this.mOnError = mOnError;
	}

	public MyAsyncTask(Context context) {

		this(context, null);
	}

	public MyAsyncTask(Context context, OnError mOnError) {

		this.mContext = context;
		this.mOnError = mOnError;
	}

	private String mErrorMessage = null;
	private String mCode = null;
	private Exception mException;

	public String getErrorMessage() {
		return mErrorMessage;
	}

	public void setErrorMessage(String mErrorMessage) {
		this.mErrorMessage = mErrorMessage;
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		this.mCode = code;
	}

	@Override
	protected T doInBackground(Void... params) {

		try {

			return callService();

		} catch (JsonParseException e) {

			mException = e;
			mErrorMessage = mContext.getString(R.string.json_error_message);
		} catch (IOException e) {

			mException = e;
			mErrorMessage = mContext.getString(R.string.web_io_error_message);
		} catch (BizException e) {

			mException = e;
			mErrorMessage = e.getDescription();
			mCode = e.getCode();
		} catch (ServiceException e) {

			mException = e;
			if (e.isClientError()) {
				mErrorMessage = mContext
						.getString(R.string.web_client_error_message);
			} else {
				mErrorMessage = mContext
						.getString(R.string.web_server_error_message);
			}
		}

		return null;
	}

	@Override
	protected void onPostExecute(T result) {

		if (mException != null) {

			if (mOnError != null) {

				mOnError.handleError(mException);
			} else {
				if (!StringUtil.isEmpty(mErrorMessage)) {

					MyToast.show(mContext, mErrorMessage);
				}
			}
		}
		try {
			onLoaded(result);
		} catch (Exception e) {

		}
	}

	@TargetApi(11)
	public void executeTask() {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			this.execute();
		} else {
			this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		}
	}
}

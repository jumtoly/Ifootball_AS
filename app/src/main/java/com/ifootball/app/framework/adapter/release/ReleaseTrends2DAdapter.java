package com.ifootball.app.framework.adapter.release;

import java.util.LinkedList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ifootball.app.framework.widget.release.ImageLoader;
import com.ifootball.app.framework.widget.release.ImageLoader.Type;
import com.ifootball.app.R;

public class ReleaseTrends2DAdapter extends BaseAdapter
{

	private Context mContext;
	private LinkedList<String> mPicUrls;

	public ReleaseTrends2DAdapter(Context mContext, LinkedList<String> mPicUrls)
	{
		super();
		this.mContext = mContext;
		this.mPicUrls = mPicUrls;
	}

	@Override
	public int getCount()
	{
		return mPicUrls.size();
	}

	@Override
	public Object getItem(int position)
	{
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHodler vHodler = new ViewHodler();
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_release_trends, null);
			vHodler.imageView = (ImageView) convertView
					.findViewById(R.id.item_release_trend_iv);
			convertView.setTag(vHodler);
		} else
		{
			vHodler = (ViewHodler) convertView.getTag();
		}
		ImageLoader.getInstance(1, Type.LIFO).loadImage(mPicUrls.get(position),
				vHodler.imageView);
		// vHodler.imageView.setImageBitmap(mBitmaps.get(position));
		return convertView;
	}

	class ViewHodler
	{
		public ImageView imageView;
	}

	public void update()
	{
		notifyDataSetChanged();
	}

}

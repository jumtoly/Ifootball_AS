package com.ifootball.app.framework.widget.release;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.ifootball.app.entity.release.ImageFloder;
import com.ifootball.app.R;

public class ListImageDirPopupWindow extends
        BasePopupWindowForListView<ImageFloder> {
    private ListView mListDir;

    public ListImageDirPopupWindow(int width, int height,
                                   List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.list_dir_lv);
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas,
                R.layout.item_release_img) {
            @Override
            public void convert(ViewHolder helper, ImageFloder item) {
                helper.setText(R.id.id_dir_item_name, item.getName().substring(1, item.getName().length()));
                helper.setImageByUrl(R.id.dir_item_image,
                        item.getFirstImagePath());
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
            }

        });
    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (mImageDirSelected != null) {
                    mImageDirSelected.selected(mDatas.get(position));
                }
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

    private abstract class CommonAdapter<T> extends BaseAdapter {
        protected LayoutInflater mInflater;
        protected Context mContext;
        protected List<T> mDatas;
        protected final int mItemLayoutId;

        public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
            this.mContext = context;
            this.mInflater = LayoutInflater.from(mContext);
            this.mDatas = mDatas;
            this.mItemLayoutId = itemLayoutId;
        }

        @Override
        public int getCount() {

            return mDatas.size();
        }

        @Override
        public T getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder = getViewHolder(position, convertView,
                    parent);
            convert(viewHolder, getItem(position));

            return viewHolder.getConvertView();
        }

        public abstract void convert(ViewHolder helper, T item);

        private ViewHolder getViewHolder(int position, View convertView,
                                         ViewGroup parent) {
            return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                    position);
        }

    }

}

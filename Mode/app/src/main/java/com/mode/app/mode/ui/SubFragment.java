package com.mode.app.mode.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mode.app.common.utils.StringUtils;
import com.mode.app.mode.R;
import com.mode.app.mode.platform.ModeFragment;

/**
 * Created by tom on 17-7-21.
 *
 * fragment for not root ui
 * which compose of title(back button,title txt/image,seperator) and content area
 *
 * content area can be inflated by xml
 */
public abstract class SubFragment extends ModeFragment {
    private RelativeLayout mRootView;
    private RelativeLayout mTitleCustomArea;
    private View mSeperatorView;
    private TextView mTvTitle;
    private InflateRelativeLayout mContentView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView =(RelativeLayout)inflater.inflate(R.layout.sub_fragment,null);
        mTitleCustomArea = (RelativeLayout)mRootView.findViewById(R.id.title_custom_area);
        mSeperatorView = (View)mRootView.findViewById(R.id.seperator);
        mTvTitle = (TextView)mRootView.findViewById(R.id.title_txt);

        mContentView = new InflateRelativeLayout(getActivity(),getContentLayoutResId());
        initUI(mContentView);
        return mRootView;
    }
    public void setTitle(String title){
        if(StringUtils.isBlank(title)){
            mSeperatorView.setVisibility(View.GONE);
        }else {
            mTvTitle.setText(title);
            mSeperatorView.setVisibility(View.VISIBLE);
        }
    }
    public void setTitle(int resId){
        mTvTitle.setText(resId);
        mSeperatorView.setVisibility(View.VISIBLE);
    }
    public abstract int getContentLayoutResId();
    public abstract void initUI(RelativeLayout contentRootView);
}

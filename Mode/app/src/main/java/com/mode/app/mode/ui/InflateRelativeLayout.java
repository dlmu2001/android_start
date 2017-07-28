package com.mode.app.mode.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by tom on 17-7-21.
 */
public class InflateRelativeLayout extends RelativeLayout {
    public InflateRelativeLayout(Context context,int resId) {
        this(context,null,resId);
    }

    public InflateRelativeLayout(Context context, AttributeSet attrs,int resId) {
        this(context, attrs,0,resId);
    }

    public InflateRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr,int resId) {
        super(context, attrs, defStyleAttr);
        init(resId);
    }
    private void init(int resId){
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(resId,this);
    }

}

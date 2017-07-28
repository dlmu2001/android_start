package com.mode.app.mode.ui;

import android.widget.RelativeLayout;

import com.mode.app.mode.R;

/**
 * Created by tom on 17-7-21.
 */
public class SignupFragment extends SubFragment {
    @Override
    public int getContentLayoutResId() {
        return R.layout.signup;
    }

    @Override
    public void initUI(RelativeLayout contentRootView) {
        setTitle(R.string.signup);
    }
}

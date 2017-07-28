package com.mode.app.mode.platform;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tom on 17-7-24.
 */

public class ModeTextView extends android.support.v7.widget.AppCompatTextView {
    public ModeTextView(Context context) {
        super(context);
    }

    public ModeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ModeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

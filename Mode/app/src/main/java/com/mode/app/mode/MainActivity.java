package com.mode.app.mode;

import android.os.Bundle;
import com.mode.app.mode.platform.ModeActivity;


import org.td21.a3party.CrashlyticsHelper;

public class MainActivity extends ModeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashlyticsHelper.init(this);

        setContentView(R.layout.activity_main);
    }
}

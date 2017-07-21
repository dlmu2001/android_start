package com.mode.app.mode;

import android.os.Bundle;

import com.mode.app.common.utils.Des3Util;
import com.mode.app.mode.account.JWTToken;
import com.mode.app.mode.network.ModeApi;
import com.mode.app.mode.network.model.Token;
import com.mode.app.mode.platform.ModeActivity;


import org.td21.a3party.CrashlyticsHelper;
import org.td21.a3party.LogUtil;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends ModeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashlyticsHelper.init(this);
        LogUtil.init(BuildConfig.DEBUG);

        setContentView(R.layout.activity_main);

    }
}

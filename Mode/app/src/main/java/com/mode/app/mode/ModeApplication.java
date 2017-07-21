package com.mode.app.mode;

import android.app.Application;
import android.content.Context;

import com.mode.app.mode.Config.Config;

import org.td21.a3party.LogUtil;

import java.io.File;

/**
 * Created by tom on 17-7-20.
 */
public class ModeApplication extends Application {
    private static ModeApplication sInstance;
    @Override
    public void onCreate() {
        super.onCreate();

        synchronized (ModeApplication.class){
            sInstance = this;
        }
    }
    public static ModeApplication getApplication(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }

    /*
     * get absolute dir path of saving app data
     */
    public final static String getAppDataDir(){
        File appDir = getAppContext().getFilesDir();

        File file = new File(appDir, Config.DATA_DIR);
        if(!file.exists()){
            if(!file.mkdir()){
                LogUtil.e("make dir failed,",Config.DATA_DIR);
            }
        }
        return file.getAbsolutePath();
    }
}

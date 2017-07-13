package org.td21.a3party;

import org.td21.a3party.timber.log.Timber;

/**
 * Created by tom on 17-7-13.
 */
public class LogUtil {
    private boolean mIsDebugVersion = true;

    private LogUtil(){
    }
    private static class LogUtilHolder{
        private static LogUtil instance=new LogUtil();
    }

    private static LogUtil getInstance(){
        return LogUtilHolder.instance;
    }

    public static void setLogEnable(boolean enable){
        getInstance().setLogEnableInner(enable);
    }
    private void setLogEnableInner(boolean enable){
        Timber.uprootAll();
        if(enable){
            Timber.plant(new Timber.DebugTree());
        }else if(!mIsDebugVersion){
            Timber.plant(new CrashlyticsTree());
        }
    }
    public static void init(boolean isDebugVersion){
        getInstance().initInner(isDebugVersion);
    }
    private void initInner(boolean isDebugVersion){
        mIsDebugVersion=isDebugVersion;
        if(isDebugVersion){
            Timber.plant(new Timber.DebugTree());
        }else {
            Timber.plant(new CrashlyticsTree());
        }
    }

}

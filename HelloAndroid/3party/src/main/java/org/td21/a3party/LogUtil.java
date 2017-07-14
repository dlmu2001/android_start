package org.td21.a3party;

import android.support.annotation.NonNull;

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

    public static void w(@NonNull String message, Object... args){
        Timber.w(message,args);
    }
    public static void w(@NonNull String tag, @NonNull String message, Object... args){
        Timber.tag(tag);
        Timber.w(message,args);
    }
    public static void w(Throwable t, @NonNull String message, Object... args){
        Timber.w(t,message,args);
    }
    public static void w(@NonNull String tag, Throwable t, @NonNull String message, Object... args){
        Timber.w(tag,t,message,args);
    }
    public static void w(Throwable t){
        Timber.w(t);
    }
    public static void w(@NonNull String tag, Throwable t){
        Timber.tag(tag);
        Timber.w(t);
    }

    public static void d(@NonNull String message, Object... args){
        Timber.d(message,args);
    }
    public static void d(@NonNull String tag, @NonNull String message, Object... args){
        Timber.tag(tag);
        Timber.d(message,args);
    }
    public static void d(Throwable t, @NonNull String message, Object... args){
        Timber.d(t,message,args);
    }
    public static void d(@NonNull String tag, Throwable t, @NonNull String message, Object... args){
        Timber.d(tag,t,message,args);
    }
    public static void d(Throwable t){
        Timber.d(t);
    }
    public static void d(@NonNull String tag, Throwable t){
        Timber.tag(tag);
        Timber.d(t);
    }

    public static void e(@NonNull String message, Object... args){
        Timber.e(message,args);
    }
    public static void e(@NonNull String tag, @NonNull String message, Object... args){
        Timber.tag(tag);
        Timber.e(message,args);
    }
    public static void e(Throwable t, @NonNull String message, Object... args){
        Timber.e(t,message,args);
    }
    public static void e(@NonNull String tag, Throwable t, @NonNull String message, Object... args){
        Timber.d(tag,t,message,args);
    }
    public static void e(Throwable t){
        Timber.d(t);
    }
    public static void e(@NonNull String tag, Throwable t){
        Timber.tag(tag);
        Timber.d(t);
    }

    public static void f(){
        Timber.d("enter");
    }

    public static void begin(@NonNull String key){
        Timber.begin(key);
    }
    public static void end(@NonNull String key) {
        Timber.end(key);
    }
}

package org.td21.a3party;

import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by tom on 17-7-7.
 */

public class CrashlyticsHelper {
    private Crashlytics mCrashlytics;
    private CrashlyticsHelper(){
        mCrashlytics=new Crashlytics();
    }
    private static class InnerHolder{
        private static CrashlyticsHelper instance = new CrashlyticsHelper();
    }
    private static CrashlyticsHelper getInstance(){
        return InnerHolder.instance;
    }
    /*
    * should be called when application start
     */
    public static void init(Context context){
        Fabric.with(context,getInstance().mCrashlytics);
    }
    /*
     * use before the exception or crash,as an additional message
     */
    public static void log(String msg){
        Crashlytics.log(msg);
    }
    /*
     * use when catch exception,only working in release mode
     */
    public static void logException(Exception e){
        Crashlytics.logException(e);
    }
}

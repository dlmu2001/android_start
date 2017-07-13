package org.td21.a3party;

import android.util.Log;

import org.td21.a3party.timber.log.Timber;

/**
 * Created by tom on 17-7-13.
 *
 * upload exception to crashlytics
 */
public class CrashlyticsTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE || priority == Log.DEBUG || priority == Log.INFO) {
            // 只分发异常
            return;
        }

        if (t == null && message != null) {
            CrashlyticsHelper.logException(new Exception(message));
        } else if (t != null && message != null) {
            CrashlyticsHelper.logException(new Exception(message, t));
        } else if (t != null) {
            CrashlyticsHelper.logException(t);
        }
    }
}

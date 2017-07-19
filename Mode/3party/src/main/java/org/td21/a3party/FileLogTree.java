package org.td21.a3party;

import org.td21.a3party.timber.log.Timber;

/**
 * Created by tom on 17-7-14.
 */
public class FileLogTree extends Timber.Tree {
    private static FileLog sFileLogInstance;
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        synchronized (FileLog.class){
            if(sFileLogInstance==null){
                sFileLogInstance = FileLog.getInstance();
                sFileLogInstance.startLogThread();
            }
        }
        String content=message;
        if(t!=null){
            content += " " + t.getMessage();
        }
        sFileLogInstance.write(priority,tag,message);
    }
}

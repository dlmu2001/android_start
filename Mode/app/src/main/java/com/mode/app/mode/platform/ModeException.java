package com.mode.app.mode.platform;

/**
 * Created by tom on 17-7-21.
 */
public class ModeException extends Exception {

    //ACCOUNT related
    public final static int NEED_LOGIN = 1001;
    public final static int PASSWORD_MISMATCH = 1002;
    public final static int OLD_PASSWORD_INCORRECT= 1003;

    private int mErrorCode;
    public ModeException(int errorCode){
        mErrorCode = errorCode;
    }
    public int getErrorCode(){
        return mErrorCode;
    }
}

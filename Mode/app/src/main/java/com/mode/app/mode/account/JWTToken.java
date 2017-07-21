package com.mode.app.mode.account;

import android.support.annotation.NonNull;

import com.mode.app.common.utils.StringUtils;
import com.mode.app.mode.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.td21.a3party.LogUtil;

/**
 * Created by tom on 17-7-20.
 *
 * JSon Web Token parser,conform to RFC7519,can be verified on https://jwt.io/
 *
 * note :we only care the second part
 */
public class JWTToken {
    private final static String SECTION_SEPERATOR="\\.";
    private final int EXPIRED_MARGIN = 60*60;

    private @NonNull String mTokenStr;
    private String mSub;
    private long mCreated;
    private long mExpired;//unit is 's'


    public JWTToken(@NonNull String tokenStr){
        mTokenStr = tokenStr;

        if(!StringUtils.isBlank(tokenStr)){
            String[] strArr = tokenStr.split(SECTION_SEPERATOR);
            if(strArr.length >=2){
                String json = StringUtils.base64Decode(strArr[1]);

                try {
                    JSONObject jsonObject = new JSONObject(json);
                    mSub= jsonObject.optString("sub",null);
                    mCreated=jsonObject.optLong("created",-1);
                    mExpired=jsonObject.optLong("exp",-1);
                } catch (JSONException e) {
                    LogUtil.e(e);
                }
            }
        }
    }

    public boolean isExpired(){
        if(mExpired<=0)
            return true;
        long curTime = System.currentTimeMillis()/1000;
        if(mExpired -curTime <EXPIRED_MARGIN){
            return true;
        }
        return false;
    }
    public @NonNull String getTokenStr(){
        return mTokenStr;
    }
}

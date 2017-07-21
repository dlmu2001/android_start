package com.mode.app.mode.account;


import com.mode.app.common.utils.Des3Util;
import com.mode.app.common.utils.PreferencesUtils;
import com.mode.app.common.utils.SerializeUtils;
import com.mode.app.common.utils.StringUtils;
import com.mode.app.mode.Config.Config;
import com.mode.app.mode.Config.PreferenceKey;
import com.mode.app.mode.ModeApplication;
import com.mode.app.mode.network.ModeApi;
import com.mode.app.mode.network.model.Token;
import com.mode.app.mode.network.model.UserInfo;
import com.mode.app.mode.platform.ModeException;


import org.td21.a3party.LogUtil;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by tom on 17-7-20.
 */
public class OAuth {
    private JWTToken mToken;//either anonymous or user token
    private boolean mIsUserToken;
    private UserInfo mUserInfo;

    private Function<Token,String> mTokenToString = new Function<Token, String>() {
        @Override
        public String apply(@NonNull Token token) throws Exception {
            return token.toString();
        }
    };
    private static class OAuthHolder{
        private static OAuth instance = new OAuth();
    }
    private OAuth(){

    }
    public static OAuth getInstance(){
        return OAuthHolder.instance;
    }
    //use des3 to encry token before save
    private void saveTokenInner(){
        if(mToken!=null){
            try {
                String encryTokenStr = Des3Util.encode(mToken.getTokenStr());
                PreferencesUtils.putString(ModeApplication.getAppContext(), PreferenceKey.ACCOUNT_TOKEN,encryTokenStr);
                PreferencesUtils.putBoolean(ModeApplication.getAppContext(),PreferenceKey.ACCOUNT_TOKEN_ISUSER,mIsUserToken);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
    }
    /*
     * persist token
     */
    public static void saveToken(){
        getInstance().saveTokenInner();
    }
    private void readTokenInner(){
        String saveStr = PreferencesUtils.getString(ModeApplication.getAppContext(),PreferenceKey.ACCOUNT_TOKEN,null);
        if(!StringUtils.isBlank(saveStr)){
            try {
                String tokenStr = Des3Util.decode(saveStr);
                mToken = new JWTToken(tokenStr);
            } catch (Exception e) {
                LogUtil.e(e);
            }
        }
        mIsUserToken = PreferencesUtils.getBoolean(ModeApplication.getAppContext(),PreferenceKey.ACCOUNT_TOKEN_ISUSER,false);
    }
    /*
     * read token from persist storage
     */
    public static void readToken(){
        getInstance().readTokenInner();
    }
    private static String getUserInfoFilePath(){
        return ModeApplication.getAppDataDir()+ File.separator+Config.USER_INFO;
    }
    public static void saveUserInfo(UserInfo userInfo){
        if(userInfo!=null){
            SerializeUtils.serialization(getUserInfoFilePath(),userInfo);
        }
    }
    public static UserInfo readUserInfor(){
        return  (UserInfo)SerializeUtils.deserialization(getUserInfoFilePath());
    }
    private void updateToken(String tokenStr,boolean isUserToken){
        mToken = new JWTToken(tokenStr);
        mIsUserToken = isUserToken;

        saveTokenInner();
    }
    /*
     * If there's exit token and is not expired
     *    return it;
     * else if(fetch anonymous token succeed)
     *    update mToken;
     *    persist token
     *    return it;
     * else
     *    return default token
     */
    private Observable<String> getTokenStrInner(){
        if(mToken!=null && !mToken.isExpired()){
            return Observable.just(mToken.getTokenStr());
        }
        return ModeApi.getAnonymousToken()
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        updateToken(s,false);
                    }
                })
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(@NonNull Throwable throwable) throws Exception {
                        return Config.DEFAULT_TOKEN;
                    }
                });

    }
    /*
     * Get Token String (not neccessary to be user token)
     *
     * If there's exit token and is not expired
     *    return it;
     * else if(fetch anonymous token succeed)
     *    update mToken;
     *    return it;
     * else
     *    return default token
     */
    public static Observable<String> getTokenStr(){
        return getInstance().getTokenStrInner();
    }

    private Observable<String> signinInner(@NonNull final String username,@NonNull final String password){
        return ModeApi.signin(username,password)
                .map(mTokenToString)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        updateToken(s,true);
                    }
                });
    }

    /**
     * sign in
     *
     * @param usrname
     * @param password
     * @return user token
     */
    public static Observable<String> signin(@NonNull final  String usrname,@NonNull final String password){
        return getInstance().signinInner(usrname,password);
    }

    private Observable<String> signup(@NonNull final String email,@NonNull final String password,final String source){
        return ModeApi.signup(email,password,source)
                .map(mTokenToString)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        updateToken(s,true);
                    }
                });

    }

    private Observable<UserInfo> getUserInfoInner(@NonNull final String authroization){
        return ModeApi.getUserInfo(authroization)
                .doOnNext(new Consumer<UserInfo>() {
                    @Override
                    public void accept(@NonNull UserInfo userInfo) throws Exception {
                        mUserInfo = userInfo;
                        saveUserInfo(userInfo);
                    }
                });
    }

    /*
     * get user information about the user with token @authroization
     */
    public static Observable<UserInfo> getUserInfo(@NonNull final String authroization){
        return getInstance().getUserInfoInner(authroization);
    }

    private Observable<String> changePasswordInner(@NonNull final String oldPassword,
                                      @NonNull final String newPassword){
        if(!mIsUserToken || mToken==null || mToken.isExpired()){
            Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                    e.onError(new ModeException(ModeException.NEED_LOGIN));
                }
            });
            return observable;
        }
        return ModeApi.changePassword(mToken.getTokenStr(),oldPassword,newPassword,newPassword);
    }
    /*
     * change password
     *
     * You should take care of the following exception
     * 1)NEED_LOGIN
     * 2)PASSWORD_MISMATCH
     * 3)OLD_PASSWORD_INCORRECT
     */
    public static Observable<String> changePassword(@NonNull final String oldPassword,
                                      @NonNull final String newPassword){
        return getInstance().changePasswordInner(oldPassword,newPassword);
    }
}

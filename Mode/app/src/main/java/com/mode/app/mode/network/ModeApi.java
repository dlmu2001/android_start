package com.mode.app.mode.network;

import com.mode.app.mode.network.model.Category;
import com.mode.app.mode.network.model.Token;
import com.mode.app.mode.network.model.UserInfo;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tom on 17-7-20.
 */
public class ModeApi {
    private final static String BASE_URL="http://test.tw.whatsmode.com/platform-4.3-user/";
    private final static String USER_AGENT="MODEAPP_Android";

    private Retrofit mRetrofit;
    private ModeService mService;
    private OkHttpClient mClient;

    private ModeApi(){
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("User-Agent",USER_AGENT)
                        .header("Accept","application/json")
                        .method(original.method(),original.body())
                        .build();
                return chain.proceed(request);

            }
        });

        mClient = httpClientBuilder.build();

        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .client(mClient)
                .build();
        mService = mRetrofit.create(ModeService.class);
    }
    private static class ModeApiHolder{
        private static ModeApi instance = new ModeApi();
    }
    private static ModeApi getInstance(){
        return ModeApiHolder.instance;
    }
    private Observable<Token> getAnonymousTokenInner(){
        return mService.getAnonymousToken();
    }
    public static Observable<String>  getAnonymousToken(){
        return getInstance().getAnonymousTokenInner()
                .map(new Function<Token, String>() {
                    @Override
                    public String apply(@NonNull Token token) throws Exception {
                        return token.mToken;
                    }
                });
    }
    private Observable<List<Category>> getCategoryList(){
        return mService.getCategoryList("search");
    }


    public static Observable<Token> signup(@NonNull final String email,@NonNull final String password,final String source){
        return getInstance().mService.signup(email,password,source);
    }

    public static Observable<Token> signin(@NonNull final String username,@NonNull final String password){
        return getInstance().mService.signin(username,password);
    }
    public static Observable<UserInfo> getUserInfo(@NonNull final String authorization){
        return getInstance().mService.getUserInfo(authorization);
    }
    public static Observable<String> changePassword(@NonNull final String authorization,@NonNull final String oldPassword,
                                                   @NonNull final String newPassword,@NonNull final String confirmPassword){
        return getInstance().mService.changePassword(authorization,oldPassword,newPassword,confirmPassword);
    }
}

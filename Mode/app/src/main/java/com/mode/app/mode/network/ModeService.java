package com.mode.app.mode.network;


import com.mode.app.mode.network.model.Category;
import com.mode.app.mode.network.model.Token;
import com.mode.app.mode.network.model.UserInfo;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by tom on 17-7-19.
 */
public interface ModeService {
    @Headers("Content-Type:application/json")
    @POST("users")
    Observable<Token> getAnonymousToken();

    @GET
    Observable<List<Category>> getCategoryList(@Query("type") String type);

    @Headers("Content-Type:application/json")
    @POST("users")
    Observable<Token> signup(@Field("email") String email,@Field("password") String password,@Field("source") String source);

    @Headers("Content-Type:application/json")
    @POST("users/token")
    Observable<Token> signin(@Header("username") String username,@Header("password") String password);

    @GET("users/self")
    Observable<UserInfo> getUserInfo(@Header("Authorization") String authorization);

    @PUT("users/self")
    Observable<String> changePassword(@Header("Authorization") String authroization,@Header("oldPassword") String oldPassword,
                                     @Header("newPassword")String newPassword,@Header("confirmPassword") String confirmPassword);
}

package com.mode.app.mode.network;

import android.util.Log;

import com.mode.app.mode.network.model.Token;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

import static org.junit.Assert.*;

/**
 * Created by tom on 17-7-20.
 */
public class ModeApiTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetAnonymousToken() throws Exception {
        Observer<Token> observer = new Observer<Token>() {

            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Token token) {
                System.out.println("hxdg onNext token "+token.mToken);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("hxdg onError "+ e.getMessage());
                assert(false);
            }

            @Override
            public void onComplete() {

            }
        };

        ModeApi.getAnonymousToken()
                .subscribe(observer);
    }


}
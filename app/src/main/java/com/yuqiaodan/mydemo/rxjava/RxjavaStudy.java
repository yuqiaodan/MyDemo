package com.yuqiaodan.mydemo.rxjava;

import android.util.Log;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by qiaodan on 2022/5/13
 * Description:
 */
public class RxjavaStudy {

    String TAG="RxjavaStudy";

    public  void start() {
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
                emitter.onNext(4);
            }
        });

        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }

            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };


        Consumer<Integer> consumerNext=new Consumer<Integer>(){

            @Override
            public void accept(Integer integer) throws Exception {

            }
        };

        Consumer<Throwable> consumerError=new Consumer<Throwable>(){
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        };


        observable.subscribe(consumerNext,consumerError);


    }

}

package com.yuqiaodan.mydemo.study;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

/**
 * Created by qiaodan on 2021/12/17
 * Description:
 */
public class ThreadStudy {
    public final static int MAG_SHOW_PROGRESS = 1;
    public final static int MAG_MAG_TEST_DELAY = 2;

    TextView tv;

    final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MAG_SHOW_PROGRESS: {
                    Log.d("ThreadStudy", "handleMessage thread id " + Thread.currentThread().getId());
                    Log.d("ThreadStudy", "msg.arg1:" + msg.arg1);
                    Log.d("ThreadStudy", "msg.arg2:" + msg.arg2);
                    String str = "当前进度：" + msg.arg1;
                    Log.d("ThreadStudy", "run: " + str);
                    tv.setText(str);
                    break;
                }
                case MAG_MAG_TEST_DELAY: {
                    Log.d("ThreadStudy", "收到msg MAG_MAG_TEST_DELAY ");
                    msg.getCallback().run();
                }

                default:
                    break;
            }
        }
    };


    public void runCountDown(TextView textView) {
        this.tv = textView;


        int test = 111;


        //simpleThread();
        Message msg1 = Message.obtain(null, new Runnable() {
            @Override
            public void run() {
                //在收到msg后调用run  等于在3秒后执行这段代码
                Log.d("ThreadStudy", "msg1 callback run!!!!" + test);
            }
        });

        msg1.what = MAG_MAG_TEST_DELAY;
        mHandler.sendMessageDelayed(msg1, 3000L);
        Log.d("ThreadStudy", "hasMsg MAG_MAG_TEST_DELAY: " + mHandler.hasMessages(MAG_MAG_TEST_DELAY));


        //纯handler 实现倒计时 测试
        Runnable countJob = new Runnable() {
            int countDown = 10;
            @Override
            public void run() {
                if (countDown > 0) {
                    mHandler.postDelayed(this, 1000L);
                    textView.setText("倒计时：" + countDown);
                    countDown--;
                } else {
                    textView.setText("倒计时结束");
                }

            }
        };
        mHandler.postDelayed(countJob, 1000L);
    }


    //简单的
    private void simpleThread() {
        Thread newThread = new Thread() {
            @Override
            public void run() {
                super.run();
                int count = 1;
                while (count <= 5) {
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    msg.what = MAG_SHOW_PROGRESS;
                    msg.arg1 = count;
                    msg.arg2 = 112211;
                    //方式1
                    //发送消息 到mHandler内处理
                    //mHandler.sendMessage(msg);

                    //方式2
                    //直接使用Runnable 到handler处理
                    String str = "当前进度：" + count;
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(str);
                        }
                    });
                    count++;
                }

            }
        };
        newThread.start();
    }


}

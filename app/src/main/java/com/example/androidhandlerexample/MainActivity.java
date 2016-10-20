package com.example.androidhandlerexample;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity {

    private Handler handler;
    private ProgressBar progressBar;
    private LooperThread mThread;

    /** Called when the activity is first created. */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
//                Log.d("TAG",Integer.toString(msg.arg1));
                progressBar.setProgress(msg.arg1);
            }
        };
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        mThread = new LooperThread();
        mThread.start();

    }


    public void incrementValue(View view) {
        final Message msgLocal = Message.obtain();
        msgLocal.arg1=1;
        mThread.mHandler.sendMessage(msgLocal);
//        new Thread(new Task()).start();
    }
    public void decrementValue(View view) {
        final Message msgLocal = Message.obtain();
        msgLocal.arg1=2;
        mThread.mHandler.sendMessage(msgLocal);
//        new Thread(new Task()).start();
    }


    class LooperThread extends Thread {
        public Handler mHandler;
        private int value = 0;

        public void run() {
            Looper.prepare();

            mHandler = new Handler() {
                public void handleMessage(Message msg) {
                    // process incoming messages here
                    if (msg.arg1 == 1) {
                        // send progress bar
                        if (value < progressBar.getMax())
                        {
                            value++;
                        }

                        final Message msgLocal = Message.obtain();
                        msgLocal.arg1 = value;
                        handler.sendMessage(msgLocal);

                        }

                    if ( msg.arg1 == 2) {
                        //
                        // send progress bar
                        if (value != 0)
                        {
                            value--;
                        }
                        final Message msgLocal = Message.obtain();
                        msgLocal.arg1 = value;
                        handler.sendMessage(msgLocal);
                    }
                }
            };

            Looper.loop();
        }
    }


}
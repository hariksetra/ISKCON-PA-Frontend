package com.giridhari.preachingassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.components.NetworkDialog;
import com.giridhari.preachingassistant.utility.ActivityManager;
import com.giridhari.preachingassistant.utility.HelperUtility;

public class SplashActivity extends Activity
{

    private static int s_timerCount = 0;
    private static Boolean s_splashFinish = false;
    private Activity act = null;
    private int mType;
    private SplashTimer m_splashtimer;
    private NetworkDialog networkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creating the timer class
        s_splashFinish = false;
        s_timerCount = 1500;
        this.act = this;

        // create new onclicklistener interface //
        NetworkDialog.networkDialogListener networkDialogListener = new NetworkDialog.networkDialogListener()
        {
            @Override
            public void onButtonClick()
            {
                if (mType == 1)
                {
                    m_splashtimer.cancel();
                    finish();
                }
                else
                {
                    m_splashtimer.start();
                }
            }
        };


        if (!HelperUtility.hasNetworkConnection(SplashActivity.this))
        {
            mType = 1;
            String primaryString = getString(R.string.no_network);
            String secondaryString = getString(R.string.check_network);
            String button = getString(R.string.close);
            networkDialog = new NetworkDialog(this, networkDialogListener, primaryString, secondaryString, button, R.drawable.pattern1);
            networkDialog.setCanceledOnTouchOutside(false);
            if (!SplashActivity.this.isFinishing())
            {
                networkDialog.show();
            }
        }
        else
        {
            mType = 0;
        }

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        resumeTimer();
        s_splashFinish = false;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (!s_splashFinish)
        {
            pauseTimer();
        }
    }

    private void pauseTimer()
    {
        m_splashtimer.cancel();
    }

    private void resumeTimer()
    {
        m_splashtimer = new SplashTimer(s_timerCount, 100);
        m_splashtimer.start();
    }

    private class SplashTimer extends CountDownTimer
    {

        public SplashTimer(long millisInFuture, long countDownInterval)
        {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish()
        {
            s_splashFinish = true;

            if (HelperUtility.hasNetworkConnection(act))
            {
                finish();
                ActivityManager.launchLogin(act);
            }

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            s_timerCount -= 50;
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (networkDialog != null && networkDialog.isShowing())
        {
            networkDialog.dismiss();
        }
    }
}

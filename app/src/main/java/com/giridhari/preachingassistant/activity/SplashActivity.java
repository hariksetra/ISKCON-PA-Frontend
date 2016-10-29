package com.giridhari.preachingassistant.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.components.NetworkDialog;
import com.giridhari.preachingassistant.utility.ActivityManager;
import com.giridhari.preachingassistant.utility.HelperUtility;

public class SplashActivity extends Activity
{

    private static int s_timerCount = 0;
    protected static Boolean s_isTimerPaused = false;
    private static Boolean s_splashFinish = false;
    private final int m_splashTime = 1500;
    private Activity act = null;
    private String TAG = "PreachingAssistant";
    private NetworkDialog.networkDialogListener networkDialogListener = null;
    private int mType;
    private SplashTimer m_splashtimer;
    NetworkDialog networkDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Creating the timer class
        s_splashFinish = false;
        s_timerCount = m_splashTime;
        this.act = this;

        // create new onclicklistener interface //
        networkDialogListener = new NetworkDialog.networkDialogListener()
        {
            @Override
            public void onButtonClick()
            {
                if (mType == 1)
                {
                    m_splashtimer.cancel();
                    finish();
                    moveTaskToBack(true);
                }
                else
                {
                    m_splashtimer.start();
                    //Start the timer for the new class
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
            networkDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            networkDialog.setCanceledOnTouchOutside(false);
            networkDialog.show();

        }
        else
        {
            mType = 0;
        }

     /*   //setting status for bootm sheet display. bad // FIXME: 10/2/2016
        SharedPreferences sharedPref = getSharedPreferences("Preaching_Assistant_Prefs#", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.KEY_BOTTOM_SHEET_STATUS, false);
        editor.apply();*/

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        resumeTimer();
        s_splashFinish = false;
        return;
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        if (!s_splashFinish)
        {
            pauseTimer();
        }
        return;
    }

    private void pauseTimer()
    {
        m_splashtimer.cancel();
        return;
    }

    private void resumeTimer()
    {
        m_splashtimer = new SplashTimer(s_timerCount, 100);
        m_splashtimer.start();
        return;
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

            return;

        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            s_timerCount -= 50;
            return;
        }
    }
}

package com.giridhari.preachingassistant.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.giridhari.preachingassistant.R;

public class SplashActivity extends Activity
{

    private static int s_timerCount = 0;
    protected static Boolean s_isTimerPaused = false;
    private static Boolean s_splashFinish = false;
    private final int m_splashTime = 1500;
    private Activity act = null;
    private String TAG = "PreachingAssistant";
    private int mType;
    private SplashTimer m_splashtimer;

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
        schneiderDialogListener = new SchneiderDialog.schneiderDialogListener()
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
                else if (mType == 2)
                {
                    if (!s_splashFinish)
                    {
                        pauseTimer();
                    }
                    startActivityForResult(new Intent(Settings.ACTION_NFC_SETTINGS), 0);
                }
                else
                {

                    m_splashtimer.start();
                    //Start the timer for the new class

                }
            }
        };



        if (!NFCUtility.isNFCAvailable(this))
        {
            mType = 1;
            String primaryString = Macros.PRIMARY_STRING_NO_NFC;
            String secondaryString = Macros.SECONDARY_STRING_NO_NFC;
            String button = Macros.CLOSE;
            d = new SchneiderDialog(this, schneiderDialogListener, primaryString, secondaryString, button, R.drawable.pattern3);
            d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            d.setCanceledOnTouchOutside(false);
            d.show();

        }
        else if (NFCUtility.isNFCAvailable(this) && !NFCUtility.isNFCTurnedOn(this))
        {
            mType = 2;
            String primaryString = Macros.PRIMARY_STRING_NFC_OFF;
            String secondaryString = Macros.SECONDARY_STRING_NFC_OFF;
            String button = Macros.SETTINGS;
            d = new SchneiderDialog(this, schneiderDialogListener, primaryString, secondaryString, button, R.drawable.pattern1);
            d.setCancelable(false);
            d.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            d.setCanceledOnTouchOutside(false);
            d.show();
        }
        else
        {
            mType = 0;
        }

        //setting status for bootm sheet display. bad // FIXME: 10/2/2016
        SharedPreferences sharedPref = getSharedPreferences("Zelio_Shared_Prefs#", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.KEY_BOTTOM_SHEET_STATUS, false);
        editor.apply();

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        //Log.d("----->", "onStart");
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //Log.d("----->", "onResume");
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

    //First time db creation
    private void firstTimedbFeed()
    {

        SchDBHandler dbHandler = new SchDBHandler(this, null, null, 1);
        UserPrefModel userPrefModel =
                new UserPrefModel("#99999@", 0, 1, "undefine", "XX@XXX.com");
        dbHandler.addProduct(userPrefModel);
        dbHandler.updateLastEpoch("0000");

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

            if (NFCUtility.isNFCAvailable(act) && NFCUtility.isNFCTurnedOn(act))
            {


                if (HelperUtility.isFirstTimeLaunch(act))
                {
                    if (HelperUtility.doesDatabaseExist(getApplicationContext(), "nfcZelioDB.db"))
                    {
                        //Log.d("Comments", "db exist");

                    }
                    else
                    {
                        //Log.d("Comments", "db don't exist");
                        firstTimedbFeed();
                        finish();
                        ActivityManager.launchLogin(act);
                    }
                }
                else
                {

                    if (HelperUtility.isRememberMeActive(getApplicationContext(), 1))
                    {
                        finish();
                        ActivityManager.AutoDetectionView(act);
                    }
                    else
                    {
                        finish();
                        ActivityManager.launchLogin(act);
                    }
                }

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

package com.giridhari.preachingassistant.utility;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SESA407978 on 8/11/2016.
 */
public class HelperUtility
{

    private static final String TAG = "HelperUtility";

    //Get device IMEI number
    public static String getDeviceId(Context pContext, Activity act)
    {
        String lRetVal;
        /*TelephonyManager lManager = (TelephonyManager) pContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (lManager != null) {
            lRetVal = lManager.getDeviceId();
            if (lRetVal == null) {
                lRetVal = Settings.Secure.getString(pContext.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }*/
        return "3595480468389201"; //hard coded , since server contains no validation or processing for this field.
    }

    // Check internet is available
    public static boolean hasNetworkConnection(Context pContext)
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager lConnectionManger = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] lNetinfo = lConnectionManger.getAllNetworkInfo();
        for (NetworkInfo lInfo : lNetinfo)
        {
            if (lInfo.getTypeName().equalsIgnoreCase("WIFI"))
            {
                if (lInfo.isConnected())
                {
                    haveConnectedWifi = true;
                }
            }
            if (lInfo.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                if (lInfo.isConnected())
                {
                    haveConnectedMobile = true;
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}

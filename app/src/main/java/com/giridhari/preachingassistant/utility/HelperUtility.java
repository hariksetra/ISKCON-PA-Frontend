package com.giridhari.preachingassistant.utility;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by SESA407978 on 8/11/2016.
 */
public class HelperUtility
{

    private static final String TAG = "HelperUtility";

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

        boolean networkConnectivity = haveConnectedWifi || haveConnectedMobile;
        boolean hasInternet = isOnline();

        return networkConnectivity && hasInternet;
    }

    private static Boolean isOnline()
    {
        /*try
        {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            return (returnVal == 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }*/
        //TODO : check why this failing ain API 23 and below.
        return true;
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

}

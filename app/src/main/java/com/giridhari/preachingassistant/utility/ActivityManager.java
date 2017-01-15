package com.giridhari.preachingassistant.utility;

import android.app.Activity;
import android.content.Intent;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.activity.BaseActivity;
import com.giridhari.preachingassistant.activity.DevoteeDetailViewActivity;
import com.giridhari.preachingassistant.activity.LoginActivity;
import com.giridhari.preachingassistant.activity.MyContactsActivity;

/**
 * Created by SESA249880 on 10/29/2016.
 */

public class ActivityManager
{
    public static void launchLogin(Activity activity)
    {
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        //animation for launching screen
        activity.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public static void logout(BaseActivity activity)
    {
        activity.clearCredentials();
        Intent intent = new Intent(activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        //animation for launching screen
        activity.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }


    public static void launchMyContactsActivity(Activity activity)
    {
        Intent intent = new Intent(activity, MyContactsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        //animation for launching screen
        activity.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

    public static void launchDevoteeDetailViewActivity(Activity activity, String selectedContactName, String selectedContactNumber, String selectedContactIntroDate)
    {
        Intent intent = new Intent(activity, DevoteeDetailViewActivity.class);
        intent.putExtra("name", selectedContactName);
        intent.putExtra("number", selectedContactNumber);
        intent.putExtra("date", selectedContactIntroDate);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivity(intent);
        //animation for launching screen
        activity.overridePendingTransition(R.anim.slide_start_right,
                R.anim.slide_end_left);
    }

}

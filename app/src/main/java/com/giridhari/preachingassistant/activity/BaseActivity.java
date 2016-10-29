package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.giridhari.preachingassistant.R;

/**
 * Created by shyam on 24/9/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotee_registration);
    }

}

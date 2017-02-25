package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.giridhari.preachingassistant.R;

public class DevoteeDetailEditActivity extends AppCompatActivity
{

    Toolbar toolbar;
    String name, number, date;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotee_detail_edit_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar_devotee_detail);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        number = bundle.getString("number");
        date = bundle.getString("date");


        FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.call);
        fab1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your call action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.save);
        fab2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your save action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_start_left, R.anim.slide_end_right);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (toolbar != null)
        {
            toolbar.setTitle(name);
        }
    }
}

package com.giridhari.preachingassistant.components;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.giridhari.preachingassistant.R;

public class NetworkDialog extends Dialog
{

    private final String mPrimaryText;
    private final String mSecondaryText;
    private final String mButton;
    private int mImageDrwable = 0;

    public NetworkDialog(Context context, networkDialogListener myclick, String primaryText, String secondaryText, String button, int image)
    {
        super(context);
        this.myListener = myclick;
        this.mPrimaryText = primaryText;
        this.mSecondaryText = secondaryText;
        this.mButton = button;
        this.mImageDrwable = image;
    }

    private final networkDialogListener myListener;

    // This is my interface //
    public interface networkDialogListener
    {
        void onButtonClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.network_layout);

        TextView primaryText = (TextView) findViewById(R.id.primaryText);
        primaryText.setText(mPrimaryText);


        TextView secondaryText = (TextView) findViewById(R.id.secondaryText);
        secondaryText.setText(mSecondaryText);

        ImageView view = (ImageView) findViewById(R.id.infoImageId);
        if (mImageDrwable != 0)
        {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(mImageDrwable);
        }
        else
        {
            view.setVisibility(View.GONE);
        }

        TextView btn = (TextView) findViewById(R.id.btn);
        btn.setText(mButton);
        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View arg0)
            {
                myListener.onButtonClick();

            }
        });
    }
}
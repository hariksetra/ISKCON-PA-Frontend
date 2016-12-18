package com.giridhari.preachingassistant.activity.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.client.PreachingAssistantService;
import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by SESA249880 on 12/18/2016.
 */

public class CaptureContactDialog extends Dialog
{

    private final Context mContext;
    EditText name, area, mobile, gender, language, feedbackEditTextBox;
    Button captureContact, fetchContact;
    private CaptureContactDialogCallback captureContactDialogCallback;
    String authToken;
    ProgressBar progressBar;
    PreachingAssistantService preachingAssistantService;


    public CaptureContactDialog(Context context)
    {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capture_contact_dialog_layout);
        TextView saveButton = (TextView) findViewById(R.id.captureContact);

        name = (EditText) findViewById(R.id.userName);
        mobile = (EditText) findViewById(R.id.mobileNumber);
        gender = (EditText) findViewById(R.id.genderValue);
        area = (EditText) findViewById(R.id.area);
        language = (EditText) findViewById(R.id.language);
        feedbackEditTextBox = (EditText) findViewById(R.id.feedbackEditTextBox);
        captureContact = (Button) findViewById(R.id.captureContact);
        //progressBar = (ProgressBar) findViewById(R.id.progressBarCaptureContact);

        captureContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://52.77.165.53/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                preachingAssistantService = retrofit.create(PreachingAssistantService.class);

                DevoteeCreateRequest devoteeCreateRequest = new DevoteeCreateRequest();
                devoteeCreateRequest.setLegalName(name.getText().toString());
                devoteeCreateRequest.setArea(area.getText().toString());
                devoteeCreateRequest.setGender(gender.getText().toString());
                devoteeCreateRequest.setSmsPhone(mobile.getText().toString());

                Log.d("Token = ", "Basic " + authToken);
                preachingAssistantService.createDevotee("Basic " + authToken, "application/json", "application/json", devoteeCreateRequest).enqueue(new Callback<DevoteeDetailsResponse>()
                {
                    @Override
                    public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response)
                    {
                        if (response.isSuccessful())
                        {
                            Log.d("response", response.message());
                            Toast.makeText(mContext, "Capture Contact successful", Toast.LENGTH_SHORT).show();
                            Log.d("CaptureContactActivity", "Capture Contact Response = " + response);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            //progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(mContext, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t)
                    {
                        t.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(mContext, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        area.setText("");
                        mobile.setText("");
                        gender.setText("");
                    }
                });
            }
        });


        setCancelable(false);
        setCanceledOnTouchOutside(false);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER);
        getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
    }

    public void onResume()
    {

        setOnKeyListener(new DialogInterface.OnKeyListener()
        {
            @Override
            public boolean onKey(android.content.DialogInterface dialog, int keyCode,
                                 android.view.KeyEvent event)
            {

                if ((keyCode == android.view.KeyEvent.KEYCODE_BACK))
                {
                    captureContactDialogCallback.setDialogDisplayStatus(false);
                    dismiss();
                    return true;
                }
                else
                {
                    return false; // pass on to be processed as normal
                }
            }
        });

    }


    public interface CaptureContactDialogCallback
    {
        void setDialogDisplayStatus(boolean isShowing);
    }


}

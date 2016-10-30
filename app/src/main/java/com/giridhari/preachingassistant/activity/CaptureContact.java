package com.giridhari.preachingassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.client.PreachingAssistantService;
import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.DevoteeListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CaptureContact extends AppCompatActivity
{

    EditText name, area, mobile, gender;
    Button captureContact, fetchContact;
    PreachingAssistantService preachingAssistantService;
    String authToken;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById(R.id.userName);
        mobile = (EditText) findViewById(R.id.mobileNumber);
        gender = (EditText) findViewById(R.id.genderValue);
        area = (EditText) findViewById(R.id.area);
        captureContact = (Button) findViewById(R.id.captureContact);
        progressBar = (ProgressBar) findViewById(R.id.progressBarCaptureContact);
        fetchContact = (Button) findViewById(R.id.fetch_contacts);
        fetchContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://52.77.165.53/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                preachingAssistantService = retrofit.create(PreachingAssistantService.class);

                preachingAssistantService.getDevoteeList("Basic " + authToken).enqueue(new Callback<DevoteeListResponse>()
                {

                    @Override
                    public void onResponse(Call<DevoteeListResponse> call, Response<DevoteeListResponse> response)
                    {
                        if (response.isSuccessful())
                        {
                            Toast.makeText(CaptureContact.this, "FetchContact successful", Toast.LENGTH_SHORT).show();
                            Log.d("FetchContact", "FetchContact Response = " + response);
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                        else
                        {
                            Toast.makeText(CaptureContact.this, "FetchContact failure", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Call<DevoteeListResponse> call, Throwable t)
                    {
                        Toast.makeText(CaptureContact.this, "FetchContact failure", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });
            }
        });


        captureContact.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                progressBar.setVisibility(View.VISIBLE);
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
                            Toast.makeText(CaptureContact.this, "Capture Contact successful", Toast.LENGTH_SHORT).show();
                            Log.d("CaptureContact", "Capture Contact Response = " + response);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            progressBar.setVisibility(View.INVISIBLE);
                            Toast.makeText(CaptureContact.this, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t)
                    {
                        t.printStackTrace();
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(CaptureContact.this, "Capture Contact Failed, please retry!!", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        area.setText("");
                        mobile.setText("");
                        gender.setText("");
                    }
                });
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onResume()
    {
        super.onResume();
        Intent myIntent = getIntent();
        authToken = myIntent.getExtras().getString("authToken");
    }

}

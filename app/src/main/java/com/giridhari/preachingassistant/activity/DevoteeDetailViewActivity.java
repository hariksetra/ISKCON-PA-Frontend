package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DevoteeDetailViewActivity extends APIActivity {

    private String devoteeUrl;

    private TextView legalNameTextView;
    private TextView initiatedNameTextView;
    private TextView phoneTextView;
    private TextView introDateTextView;
    private TextView genderTextView;
    private TextView areaTextView;
    private TextView dobTextView;
    private TextView maritalStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devotee_detail_view);

        devoteeUrl = getIntent().getStringExtra("devoteeUrl");

        init();

        if(devoteeUrl != null) {
            getDevoteeDetails();
        }
    }

    private void init() {
        this.legalNameTextView = (TextView) findViewById(R.id.legal_name);
        this.initiatedNameTextView = (TextView) findViewById(R.id.initiated_name);
        this.phoneTextView = (TextView) findViewById(R.id.phone);
        this.introDateTextView = (TextView) findViewById(R.id.intro_date);
        this.genderTextView = (TextView) findViewById(R.id.gender);
        this.areaTextView = (TextView) findViewById(R.id.area);
        this.dobTextView = (TextView) findViewById(R.id.dob);
        this.maritalStatusTextView = (TextView) findViewById(R.id.marital_status);
    }

    private void getDevoteeDetails() {
        preachingAssistantService.getDevoteeDetails(getStringFromSharedPreferences(LoginActivity.AUTH_TOKEN), devoteeUrl).enqueue(new Callback<DevoteeDetailsResponse>() {
            @Override
            public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response) {
                if(response != null && response.isSuccessful()) {
                    DevoteeDetailsResponse devoteeDetailsResponse = response.body();
                    updateView(devoteeDetailsResponse);
                }
            }

            @Override
            public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t) {

            }
        });
    }

    private void updateView(DevoteeDetailsResponse devoteeDetailsResponse) {
        this.legalNameTextView.setText(devoteeDetailsResponse.getLegalName());
        this.initiatedNameTextView.setText(devoteeDetailsResponse.getInitiatedName());
        this.phoneTextView.setText(devoteeDetailsResponse.getSmsPhone());
        this.introDateTextView.setText(devoteeDetailsResponse.getIntroDate().toString());
        this.genderTextView.setText(devoteeDetailsResponse.getGender());
        this.areaTextView.setText(devoteeDetailsResponse.getArea());
        // this.dobTextView.setText(devoteeDetailsResponse.getDob().toString());
        this.maritalStatusTextView.setText(devoteeDetailsResponse.getMaritalStatus());
    }

}

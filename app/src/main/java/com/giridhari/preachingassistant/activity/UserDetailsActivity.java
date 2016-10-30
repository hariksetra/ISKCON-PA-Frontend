package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.util.Log;

import com.giridhari.preachingassistant.R;
import com.giridhari.preachingassistant.model.DevoteeCreateRequest;
import com.giridhari.preachingassistant.model.DevoteeDetailsResponse;
import com.giridhari.preachingassistant.model.DevoteeListResponse;
import com.giridhari.preachingassistant.model.UserAccountDetailResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends APIActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        DevoteeCreateRequest devoteeCreateRequest = new DevoteeCreateRequest();
        devoteeCreateRequest.setLegalName("Pavan");
        devoteeCreateRequest.setArea("Bangalore");
        devoteeCreateRequest.setGender("MALE");
        devoteeCreateRequest.setSmsPhone("9878675645");

        preachingAssistantService.createDevotee("Basic YWRtaW46YWRtaW4=", "application/json", "application/json", devoteeCreateRequest).enqueue(new Callback<DevoteeDetailsResponse>() {
            @Override
            public void onResponse(Call<DevoteeDetailsResponse> call, Response<DevoteeDetailsResponse> response) {
                Log.d("response", response.message());
            }

            @Override
            public void onFailure(Call<DevoteeDetailsResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
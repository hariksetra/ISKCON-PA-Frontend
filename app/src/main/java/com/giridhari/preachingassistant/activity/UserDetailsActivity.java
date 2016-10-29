package com.giridhari.preachingassistant.activity;

import android.os.Bundle;
import android.util.Log;

import com.giridhari.preachingassistant.R;
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

        UserAccountDetailResponse userAccountDetailResponse = new UserAccountDetailResponse();

        preachingAssistantService.getDevoteeList("Basic YWRtaW46YWRtaW4=").enqueue(new Callback<DevoteeListResponse>() {
            @Override
            public void onResponse(Call<DevoteeListResponse> call, Response<DevoteeListResponse> response) {
                Log.d("Response", response.toString());
            }

            @Override
            public void onFailure(Call<DevoteeListResponse> call, Throwable t) {
                Log.e("Error", t.getMessage());
            }
        });
    }
}
;